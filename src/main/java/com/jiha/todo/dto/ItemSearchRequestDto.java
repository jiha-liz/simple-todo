package com.jiha.todo.dto;

import lombok.Data;

@Data
public class ItemSearchRequestDto {


    private String content;

    private Boolean completeYn;

    private int page = 0;

    private int size = 5;

//    private Sort sort = Sort.by(Sort.Direction.ASC, "ID");

}
