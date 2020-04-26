package com.jiha.todo.controller;

import com.jiha.todo.dto.ItemRequestDto;
import com.jiha.todo.dto.ItemSearchRequestDto;
import com.jiha.todo.service.ItemService;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    /**
     * to-do card update
     */
    @PutMapping
    public ResponseEntity updateCard(@RequestBody ItemRequestDto requestDto){
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

    /**
     * 카드 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCard(@PathVariable("id") Long id){
        itemService.delete(id);
        return ResponseEntity.ok(Response.SC_OK);
    }

    /**
     * 검색
     */
    @GetMapping("/list")
    public ResponseEntity list(ItemSearchRequestDto searchRequestDto){
        return ResponseEntity.ok(itemService.getList(searchRequestDto));
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> download(String param) throws IOException {
        File file = itemService.downloadData();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    /**
     * 파일 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity upload(MultipartFile file) throws IOException {
        itemService.upload(file);
        return ResponseEntity.ok(Response.SC_OK);
    }

}
