package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.Item;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.repository.IItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
}