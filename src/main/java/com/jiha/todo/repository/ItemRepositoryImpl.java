package com.jiha.todo.repository;

import com.jiha.todo.domain.Item;
import com.jiha.todo.domain.QItem;
import com.jiha.todo.dto.ItemRequestDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {


    public ItemRepositoryImpl() {
        super(ItemRepositoryImpl.class);
    }


    @Override
    public Page<Item> findPageList(ItemRequestDto requestDto) {
        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item);


        return null;
    }


}
