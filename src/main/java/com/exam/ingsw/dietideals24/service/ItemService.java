package com.exam.ingsw.dietideals24.service;

import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.helper.RequestedItemDTO;
import com.exam.ingsw.dietideals24.repository.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

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
    public byte[] retrieveByteArray(String searchTerm, List<String> categories) {
        return itemRepository.getImageContent(searchTerm, categories);
    }

    @Override
    public List<RequestedItemDTO> findItemsUpForAuction(String searchTerm, List<String> categories) {
        List<Object[]> resultList = itemRepository.findItemsBySearchTermAndCategories(searchTerm, categories);

        List<RequestedItemDTO> itemDTOS = new ArrayList<>();
        for (Object[] object : resultList) {
            RequestedItemDTO itemDTO = new RequestedItemDTO();
            itemDTO.setItemId((Integer) object[0]);
            itemDTO.setName((String) object[1]);
            itemDTO.setDescription((String) object[2]);
            itemDTO.setBasePrize((Float) object[3]);
            itemDTO.setCategory((String) object[4]);
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }
}