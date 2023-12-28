package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.Item;

import java.util.List;

public interface IItemService {
    Integer createItem(Item item);
    List<Item> findItemsUpForAuction(String searchTerm, List<String> selectedCategories);
}