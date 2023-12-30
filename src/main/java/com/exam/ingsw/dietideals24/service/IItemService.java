package com.exam.ingsw.dietideals24.service;

import java.util.List;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.helper.RequestedItemDTO;

public interface IItemService {
    Integer createItem(Item item);

    /* [FEATURED ITEMS UP FOR AUCTION SECTION] */
    List<RequestedItemDTO> findItemsUpForFeaturedAuction(String searchTerm, List<String> selectedCategories, Integer userId);
    byte[] findItemImageContent(Integer itemId, String name);
}