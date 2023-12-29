package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.service.IItemService;
import com.exam.ingsw.dietideals24.model.helper.RequestedItemDTO;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import com.exam.ingsw.dietideals24.exception.ImageContentIsNullException;

@RestController
public class ItemController {
    private byte[] itemImageContent;

    @Autowired
    @Qualifier("ItemService")
    private IItemService itemService;

    @PostMapping("/item/saveItemImage")
    public void retrieveItemImageContent(@RequestBody byte[] itemImageContent) {
        this.itemImageContent = itemImageContent;
    }

    @PostMapping("/item/addItem")
    public ResponseEntity<Integer> createItem(@RequestBody RequestedItemDTO requestedItem) throws ImageContentIsNullException {
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

    @GetMapping("/item/imageContent")
    public ResponseEntity<byte[]> retrieveItemImageContent(
            @RequestParam String searchTerm,
            @RequestParam(required = false) List<String> categories) throws EmptyParametersException {
        if (searchTerm.isEmpty() || searchTerm.isBlank()) throw new EmptyParametersException("\"searchTerm\" cannot be null or empty string!");
        else {
            byte[] content = itemService.retrieveByteArray(searchTerm, categories);
            return ResponseEntity.ok(content);
        }
    }

    @GetMapping("/item/findItemsUpForAuction")
    public ResponseEntity<List<RequestedItemDTO>> findItemsUpForAuctions(
            @RequestParam String searchTerm,
            @RequestParam(required = false) List<String> categories) throws EmptyParametersException {
        if (searchTerm.isEmpty() || searchTerm.isBlank()) throw new EmptyParametersException("\"searchTerm\" cannot be null or empty string!");
        else {
            List<RequestedItemDTO> itemDTOS = itemService.findItemsUpForAuction(searchTerm, categories);
            return ResponseEntity.ok(itemDTOS);
        }
    }
}