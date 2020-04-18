package com.jiha.todo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemRequestDto {

    private Long id;

    private String content;

    private List<Long> refItems;

}
