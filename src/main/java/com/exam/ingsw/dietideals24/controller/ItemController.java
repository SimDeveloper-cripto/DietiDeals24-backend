package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.service.IItemService;
import com.exam.ingsw.dietideals24.model.helper.ItemDTO;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
    public ResponseEntity<Integer> createItem(@RequestBody ItemDTO requestedItem) throws ImageContentIsNullException {
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

    /* [FEATURED ITEMS UP FOR AUCTION SECTION] */
    @GetMapping("/item/findItemsUpForFeaturedAuction")
    public ResponseEntity<List<ItemDTO>> findItemsUpForAuctions(
            @RequestParam String searchTerm,
            @RequestParam(required = false) List<String> categories,
            @RequestParam Integer userId) throws EmptyParametersException {
        if (searchTerm.isEmpty() || searchTerm.isBlank()) throw new EmptyParametersException("\"searchTerm\" cannot be null or empty string!");
        else if (userId == null)  throw new EmptyParametersException("User's ID cannot be null!");
        else {
            List<ItemDTO> items = itemService.findItemsUpForFeaturedAuction(searchTerm, categories, userId);
            return ResponseEntity.ok(items);
        }
    }

    @GetMapping("/item/findAuctionedByUser")
    public ResponseEntity<List<ItemDTO>> findItemsCreatedByUser(@RequestParam User user) {
        List<ItemDTO> items = itemService.findItemsCreatedByUser(user);
        return ResponseEntity.ok(items);
    }

    /*
        @GetMapping("/item/findItemsForWhichTheUserPartecipateAuction")
        public ResponseEntity<List<ItemDTO>> findItemsForUser( // I don't want to make the name too long
                @RequestParam Integer userId,
                @RequestParam String email,
                @RequestParam String password) throws EmptyParametersException {
            if (userId == null || (email.isEmpty() || email.isBlank()) || (password.isEmpty() || password.isBlank())) {
                throw new EmptyParametersException("At least one of the parameters provided is NULL or empty string!");
            } else {
                List<ItemDTO> items = itemService.findItemsUserWants(userId, email, password);
                return ResponseEntity.ok(items);
            }
        }
     */

    @GetMapping("/item/findItemImage")
    public ResponseEntity<byte[]> findItemImage(
            @RequestParam Integer itemId,
            @RequestParam String name) throws EmptyParametersException {
        if (itemId == null) throw new EmptyParametersException("ItemID cannot be null");
        else if (name.isEmpty() || name.isBlank()) throw new EmptyParametersException("Item's name cannot be null or empty string!");
        else  {
            byte[] imageContent = itemService.findItemImageContent(itemId, name);
            return ResponseEntity.ok(imageContent);
        }
    }
}