package com.jiha.todo.repository;


import com.jiha.todo.domain.Item;
import com.jiha.todo.dto.ItemSearchRequestDto;
import org.springframework.data.domain.Page;

public interface ItemRepositoryCustom {


    Page<Item> findPageList (ItemSearchRequestDto searchRequestDto);

}
