package com.jiha.todo.service;

import com.jiha.todo.domain.Item;
import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.dto.ItemResponseDto;
import com.jiha.todo.dto.ItemSearchRequestDto;
import com.jiha.todo.dto.ItemSearchResponseDto;
import com.jiha.todo.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ItemService {

    private ItemRepository itemRepository;

    private ModelMapper modelMapper;

    @Transactional
    public void create(ItemRequestDto requestDto){
        Item item = modelMapper.map(requestDto, Item.class);
        convertRefItem(requestDto.getRefItems(), item);
        itemRepository.save(item);
    }

    private Item isValidItem(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(()-> new ValidationException("등록되지 않은 카드 ID입니다."));
    }

    public ItemSearchResponseDto getList(ItemSearchRequestDto searchRequestDto) {
        Page<Item> list= itemRepository.findPageList(searchRequestDto);

        ItemSearchResponseDto responseDto = new ItemSearchResponseDto();
        responseDto.setPageable(list.getPageable());
        responseDto.setTotalElements(list.getTotalElements());
        responseDto.setTotalPages(list.getTotalPages());
        responseDto.setList(list.getContent().stream().map(item -> modelMapper.map(item, ItemResponseDto.class))
                .collect(Collectors.toList()));
     return responseDto;
    }

    @Transactional
    public void statusUpdate(Long id, boolean completeYn) {
        Item item = isValidItem(id);
        if(completeYn){
            if(item.getRefItems().stream().anyMatch(ref -> !ref.isCompleteYn())) throw new ValidationException("참조카드 완료 후 완료가능합니다.");
        }
        item.setCompleteYn(completeYn);
    }

    @Transactional
    public void update(ItemRequestDto requestDto) {
        Item item = isValidItem(requestDto.getId());
        item.setContents(requestDto.getContents());
        convertRefItem(requestDto.getRefItems(), item);

        if(item.getRefItems().stream().anyMatch(ref -> !ref.isCompleteYn())){
            item.setCompleteYn(false);
        }
        itemRepository.save(item);

    }

    /**
     * id값만 있는 리스트를 유효성 검사 후 item으로 변환하여 참조키로 넣음
     */
    private void convertRefItem(List<Long> refItems, Item item) {
        if (!refItems.isEmpty()) {
            item.getRefItems().clear();
            for (Long itemId : refItems) {
                isValidItem(itemId);
                if(item.getId() != null && itemId == item.getId()) throw new ValidationException("본인카드를 참조할 수 없습니다.");
                item.addRefItem(itemId);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        isValidItem(id);
        itemRepository.deleteById(id);
    }
}
