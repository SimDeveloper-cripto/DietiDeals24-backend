package com.exam.ingsw.dietideals24.service;

import java.util.ArrayList;
import java.util.List;

import com.exam.ingsw.dietideals24.model.User;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.repository.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.model.helper.ItemDTO;

@Service("ItemService")
public class ItemService implements IItemService {
    @Autowired
    private IItemRepository itemRepository;

    @Override
    public Integer createItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getItemId();
    }

    @Override
    public List<ItemDTO> findItemsUpForFeaturedAuctionBySearchTermAndCategory(String searchTerm, List<String> categories, Integer userId) {
        List<Object[]> result = itemRepository.findFeaturedItemsBySearchTermAndCategory(searchTerm, categories, userId);
        return createItemList(result);
    }

    @Override
    public List<ItemDTO> findItemsUpForFeaturedAuction(Integer userId, String email) {
        List<Object[]> result = itemRepository.findFeaturedItems(userId);
        return createItemList(result);
    }

    @Override
    public List<ItemDTO> findItemsCreatedByUser(Integer userId, String email) {
        List<Object[]> result = itemRepository.findCreatedByUserItems(userId, email);
        return createItemList(result);
    }

    @Override
    public List<ItemDTO> findItemsWantedByUser(Integer userId, String email, String password) {
        List<Object[]> result = itemRepository.findItemsForUser(userId, email, password);
        return createItemList(result);
    }

    @Override
    public byte[] findItemImageContent(Integer itemID, String name) {
        return itemRepository.findImageByIdAndName(itemID, name);
    }

    private List<ItemDTO> createItemList(List<Object[]> result) {
        List<ItemDTO> items = new ArrayList<>();
        for (Object[] obs : result) {
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemId((Integer) obs[0]);
            itemDTO.setName((String) obs[1]);
            itemDTO.setDescription((String) obs[2]);
            itemDTO.setCategory((String) obs[3]);
            itemDTO.setBasePrize((Float) obs[4]);
            itemDTO.setUser((User) obs[5]);
            items.add(itemDTO);
        }
        return items;
    }
}