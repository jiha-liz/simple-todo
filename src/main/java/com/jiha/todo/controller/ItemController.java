package com.jiha.todo.controller;

import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.service.ItemService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity createCard(ItemRequestDto requestDto){

        itemService.create(requestDto);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity updateCard(ItemRequestDto requestDto){

        itemService.update(requestDto);
        return ResponseEntity.ok("success");
    }


    @PutMapping("/{id}")
    public ResponseEntity statusCheck(@PathVariable("id") Long id, @RequestParam("complete") boolean completeYn){

        return ResponseEntity.ok( itemService.statusCheck(id, completeYn));
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
