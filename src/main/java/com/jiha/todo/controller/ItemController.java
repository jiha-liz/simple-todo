package com.jiha.todo.controller;

import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.service.ItemService;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;

    /**
     * to-do card create
     */
    @PostMapping
    public ResponseEntity createCard(@RequestBody ItemRequestDto requestDto){
        itemService.create(requestDto);
        return ResponseEntity.ok(Response.SC_OK);
    }

    @PutMapping
    public ResponseEntity updateCard(ItemRequestDto requestDto){

        itemService.update(requestDto);
        return ResponseEntity.ok(Response.SC_OK);
    }

    /**
     * 완료/미완료 상태 업데이트
     */
    @PutMapping("/{id}")
    public ResponseEntity statusUpdate(@PathVariable("id") Long id, @RequestParam("complete") boolean completeYn){
        itemService.statusUpdate(id, completeYn);
        return ResponseEntity.ok(Response.SC_OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCard(@PathVariable("id") Long id){
        itemService.delete(id);
        return ResponseEntity.ok( "success");
    }


    //검색 필터 추가
    @GetMapping("/list")
    public ResponseEntity list(){
        return ResponseEntity.ok(itemService.getList());
    }


}
