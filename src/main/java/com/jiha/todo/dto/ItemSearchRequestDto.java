package com.jiha.todo.dto;

import lombok.Data;

@Data
public class ItemSearchRequestDto {


    private String contents;

    private Boolean completeYn;

    private int page = 0;

    private int size = 5;

    private String sort;

    private String ascending;

}
