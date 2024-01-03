package com.exam.ingsw.dietideals24.service;

import java.util.List;
import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.helper.ItemDTO;

public interface IItemService {
    Integer createItem(Item item);

    /* [FEATURED ITEMS UP FOR AUCTION SECTION] */
    List<ItemDTO> findItemsUpForFeaturedAuction(String searchTerm, List<String> selectedCategories, Integer userId);

    /* [ONLY CREATED BY USER ITEMS UP FOR AUCTION] */
    List<ItemDTO> findItemsCreatedByUser(User user);

    /* [ITEMS FOR WICH THE USER PARTECIPATES IN AN AUCTION] */
    List<ItemDTO> findItemsUserWants(Integer userId, String email, String password);

    byte[] findItemImageContent(Integer itemId, String name);
}