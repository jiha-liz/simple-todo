package com.jiha.todo.repository;

import com.jiha.todo.domain.Item;
import com.jiha.todo.domain.QItem;
import com.jiha.todo.dto.ItemSearchRequestDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {


    public ItemRepositoryImpl() {
        super(ItemRepositoryImpl.class);
    }


    @Override
    public Page<Item> findPageList(ItemSearchRequestDto searchRequestDto) {
        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item);
        if(searchRequestDto.getCompleteYn() != null) query.where(item.completeYn.eq(searchRequestDto.getCompleteYn()));
        if(!searchRequestDto.getContents().isEmpty()) query.where(item.contents.contains(searchRequestDto.getContents()));

        Order order = searchRequestDto.getAscending().equals("asc")? Order.ASC : Order.DESC ;

        OrderSpecifier orderSpecifier ;
        switch (searchRequestDto.getSort()) {
            case "createTime":
                orderSpecifier = new OrderSpecifier(order, item.createTime);
                break;
            case "updateTime":
                orderSpecifier = new OrderSpecifier(order, item.updateTime);
                break;
            case "contents":
                orderSpecifier = new OrderSpecifier(order, item.contents);
                break;
            default:
                orderSpecifier = new OrderSpecifier(order, item.id);
                break;
        }

        QueryResults<Item> queryResults = query.offset(searchRequestDto.getPage())
                .limit(searchRequestDto.getSize())
                .orderBy(orderSpecifier) .fetchResults();

        Pageable pageable = PageRequest.of(searchRequestDto.getPage(), searchRequestDto.getSize());
        return  new PageImpl<>(queryResults.getResults(), pageable, query.fetchCount());
    }


}
