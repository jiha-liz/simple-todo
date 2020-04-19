package com.jiha.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemResponseDto {

    private Long id;

    private String content;

    private List<ItemResponseDto> refItems;

    private boolean completeYn;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;

}
