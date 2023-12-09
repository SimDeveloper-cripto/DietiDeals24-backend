package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.service.IItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@RestController
public class RegisterItemController {
    @Autowired
    @Qualifier("ItemService")
    private IItemService itemService;

    @PostMapping("/saveItem")
    public ResponseEntity<Void> createItem(@RequestBody Item item) {
        itemService.createItem(item);
        return ResponseEntity.ok().build();
    }
}