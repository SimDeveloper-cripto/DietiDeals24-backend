package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.service.IItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterItemController {
    @Autowired
    @Qualifier("ItemService")
    private IItemService itemService;

    @PostMapping("/addItem")
    public ResponseEntity<Void> registerItem(@RequestBody Item item) {
        return itemService.registerItem(item);
    }
}