package com.exam.ingsw.dietideals24.service;

import java.util.ArrayList;
import java.util.List;

import com.exam.ingsw.dietideals24.model.User;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.repository.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.model.helper.RequestedItemDTO;

@Service("ItemService")
public class ItemService implements IItemService {
    @Autowired
    private IItemRepository itemRepository;

    public ItemService() {}

    @Override
    public Integer createItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getItemId();
    }

    @Override
    public List<RequestedItemDTO> findItemsUpForFeaturedAuction(String searchTerm, List<String> categories, Integer userId) {
        List<Object[]> result = itemRepository.findItemsForFeaturedAuction(searchTerm, categories, userId);

        List<RequestedItemDTO> items = new ArrayList<>();
        for (Object[] record : result) {
            RequestedItemDTO itemDTO = new RequestedItemDTO();

            itemDTO.setItemId((Integer) record[0]);
            itemDTO.setName((String) record[1]);
            itemDTO.setDescription((String) record[2]);
            itemDTO.setCategory((String) record[3]);
            itemDTO.setBasePrize((Float) record[4]);
            itemDTO.setUser((User) record[5]);
            items.add(itemDTO);
        }

        return items;
    }

    @Override
    public byte[] findItemImageContent(Integer itemID, String name) {
        return itemRepository.findImageByIdAndName(itemID, name);
    }
}