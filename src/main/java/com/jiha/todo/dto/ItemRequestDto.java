package com.jiha.todo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemRequestDto {

    private Long id;

    private String contents;

    private List<Long> refItems = new ArrayList<>();

}
