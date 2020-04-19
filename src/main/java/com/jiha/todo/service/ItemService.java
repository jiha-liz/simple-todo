package com.jiha.todo.service;

import com.jiha.todo.domain.Item;
import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.dto.ItemResponseDto;
import com.jiha.todo.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void create(ItemRequestDto requestDto){
        Item item = modelMapper.map(requestDto, Item.class);
        if(!requestDto.getRefItems().isEmpty()) {
            for (Long itemId : requestDto.getRefItems()) {
                isValidItem(itemId);
                item.addRefItem(itemId);
            }
        }
        itemRepository.save(item);
    }

    private Item isValidItem(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(()-> new ValidationException("등록되지 않은 카드 ID입니다."));
    }

    public List<ItemResponseDto> getList() {

        //페이징, 검색, 정렬 들어가야함
     return itemRepository.findAll().
             stream().map(item -> modelMapper.map(item, ItemResponseDto.class))
             .collect(Collectors.toList());
    }

    @Transactional
    public void statusUpdate(Long id, boolean completeYn) {
        Item item = isValidItem(id);
        if(completeYn){
            if(item.getRefItems().stream().anyMatch(ref -> !ref.isCompleteYn())) throw new ValidationException("참조카드 완료 후 완료가능합니다.");
        }
        item.setCompleteYn(completeYn);
    }

    public void update(ItemRequestDto requestDto) {

        Item item = modelMapper.map(requestDto, Item.class);
        itemRepository.save(item);

    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
