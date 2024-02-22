package com.exam.ingsw.dietideals24.service.serviceinterface;

import java.util.List;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.dto.ItemDTO;

public interface IItemService {
    Integer createItem(Item item);

    /* [FEATURED ITEMS UP FOR AUCTION SECTION] */
    List<ItemDTO> findItemsUpForFeaturedAuctionBySearchTermAndCategory(String searchTerm, List<String> selectedCategories, int userId);

    List<ItemDTO> findItemsUpForFeaturedAuction(Integer userId, String email);

    /* [ONLY CREATED BY USER ITEMS UP FOR AUCTION] */
    List<ItemDTO> findItemsCreatedByUser(Integer userId, String email);

    /* [ITEMS FOR WICH THE USER PARTECIPATES IN AN AUCTION] */
    List<ItemDTO> findItemsWantedByUser(Integer userId, String email, String password);

    byte[] findItemImageContent(Integer itemId, String name);

    List<ItemDTO> findItemsWithNoWinner(Integer userId);

    List<ItemDTO> findItemsWonByUser(Integer userId);
}