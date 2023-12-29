package com.exam.ingsw.dietideals24.service;

import java.util.List;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.helper.RequestedItemDTO;

public interface IItemService {
    Integer createItem(Item item);
    byte[] retrieveByteArray(String searchTerm, List<String> categories);
    List<RequestedItemDTO> findItemsUpForAuction(String searchTerm, List<String> selectedCategories);
}