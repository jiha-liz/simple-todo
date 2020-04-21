package com.jiha.todo.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class ItemSearchResponseDto {


    private List<ItemResponseDto> list;

    private Pageable pageable;

    private int totalPages;

    private long totalElements;

}
