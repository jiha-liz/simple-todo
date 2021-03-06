package com.jiha.todo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jiha.todo.domain.Item;
import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.dto.ItemResponseDto;
import com.jiha.todo.dto.ItemSearchRequestDto;
import com.jiha.todo.dto.ItemSearchResponseDto;
import com.jiha.todo.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public File downloadData() throws IOException {
        File file = new File("todo-backup.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String line = mapper.writeValueAsString(itemRepository.findAll());
        writer.write(line);
        writer.flush();
        writer.close();
        return file;
    }

    /**
     * localDateTime이슈
     */
    @Transactional
    public void upload(MultipartFile file) throws IOException {
        String ext = getExt(file.getOriginalFilename());
        if(!ext.equals("txt")) throw new ValidationException("txt 파일만 가능합니다.");
        else throw new ValidationException("미완료 기능입니다.");
        
//        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//        String line = reader.readLine();
//        ObjectMapper mapper = new ObjectMapper();
//
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        List<ItemTxtDto> items =mapper.readValue(line, new TypeReference<List<ItemTxtDto>>() {
//        });
//
//        items.stream().forEach(System.out::println);

    }

    private String getExt(String fileName){
        int type = fileName.lastIndexOf(".");
        String ext = fileName.substring(type + 1);
        return ext;

    }
}
