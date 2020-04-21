package com.jiha.todo.repository;

import com.jiha.todo.domain.Item;
import com.jiha.todo.domain.QItem;
import com.jiha.todo.dto.ItemSearchRequestDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {


    public ItemRepositoryImpl() {
        super(ItemRepositoryImpl.class);
    }


    @Override
    public Page<Item> findPageList(ItemSearchRequestDto searchRequestDto) {
        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item);
        if(searchRequestDto.getCompleteYn() != null) query.where(item.completeYn.eq(searchRequestDto.getCompleteYn()));
        if(!searchRequestDto.getContent().isEmpty()) query.where(item.content.contains(searchRequestDto.getContent()));

        Pageable pageable = PageRequest.of(searchRequestDto.getPage(), searchRequestDto.getSize());

        QueryResults<Item> queryResults = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetchResults();
        return  new PageImpl<>(queryResults.getResults(), pageable, query.fetchCount());
    }


}
