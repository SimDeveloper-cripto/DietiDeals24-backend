package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.Item;
import org.springframework.http.ResponseEntity;

public interface IItemService {
    ResponseEntity<Item> registerItem(Item item);
}