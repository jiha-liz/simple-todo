package com.jiha.todo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemSearchRequestDto {


    private String content;

    private Boolean completeYn;

}
