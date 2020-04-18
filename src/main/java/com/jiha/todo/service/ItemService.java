package com.jiha.todo.service;

import com.jiha.todo.domain.Item;
import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.dto.ItemResponseDto;
import com.jiha.todo.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    public void create(ItemRequestDto requestDto){

        Item item = modelMapper.map(requestDto, Item.class);
        itemRepository.save(item);

    }

    public List<ItemResponseDto> getList() {

        //페이징, 검색, 정렬 들어가야함
     return itemRepository.findAll().
             stream().map(item -> modelMapper.map(item, ItemResponseDto.class))
             .collect(Collectors.toList());
    }

    public boolean statusCheck(Long id, boolean completeYn) {
        Item item = itemRepository.findById(id).orElse(null);
        if(item == null ) return false;
        if(completeYn){
            if(item.getRefItems().stream().anyMatch(ref -> !ref.isCompleteYn())) return false;
        }

        item.setCompleteYn(completeYn);

        return true;
    }

    public void update(ItemRequestDto requestDto) {

        Item item = modelMapper.map(requestDto, Item.class);
        itemRepository.save(item);

    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
