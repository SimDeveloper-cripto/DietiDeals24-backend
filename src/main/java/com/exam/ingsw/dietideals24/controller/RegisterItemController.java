package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.service.IItemService;
import com.exam.ingsw.dietideals24.model.helper.RequestedItem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.ingsw.dietideals24.exception.ImageContentIsNullException;

@RestController
public class RegisterItemController {
    private byte[] itemImageContent;

    @Autowired
    @Qualifier("ItemService")
    private IItemService itemService;

    @PostMapping("/item/saveItemImage")
    public void retrieveItemImageContent(@RequestBody byte[] itemImageContent) {
        this.itemImageContent = itemImageContent;
    }

    @PostMapping("/item/addItem")
    public ResponseEntity<Integer> createItem(@RequestBody RequestedItem requestedItem) throws ImageContentIsNullException {
        Item item = new Item();
        item.setName(requestedItem.getName());
        item.setDescription(requestedItem.getDescription());
        item.setCategory(requestedItem.getCategory());
        item.setBasePrize(requestedItem.getBasePrize());
        item.setUser(requestedItem.getUser());

        if (itemImageContent != null)
            item.setImage(itemImageContent);
        else
            throw new ImageContentIsNullException("The image of the Item received is null!");

        Integer itemId = itemService.createItem(item);
        return ResponseEntity.ok(itemId);
    }
}