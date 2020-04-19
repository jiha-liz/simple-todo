package com.jiha.todo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemResponseDto {

    private Long id;

    private String content;

    private List<ItemResponseDto> refItems;

    private boolean completeYn;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
