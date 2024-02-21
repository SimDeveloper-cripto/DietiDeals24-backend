package com.exam.ingsw.dietideals24.JUnit;

import java.util.List;
import java.util.ArrayList;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.service.ItemService;
import com.exam.ingsw.dietideals24.model.helper.ItemDTO;
import com.exam.ingsw.dietideals24.repository.IItemRepository;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService itemService;
    @Mock
    private IItemRepository itemRepository;

    @Test
    public void testCaseCE1_CE4_CE6() {
        String searchTerm       = "Bicicletta";
        int userId              = 2;
        List<String> categories = new ArrayList<>();
        categories.add("Collezionismo");

        User owner = new User();
        owner.setUserId(userId);
        Object[] retrievedObject = new Object[] {
                3,
                "Bicicletta",
                "Bicicletta utilizzata di mio nipote",
                "Collezionismo",
                0.0f,
                owner
        };

        when(itemRepository.findFeaturedItemsBySearchTermAndCategory(searchTerm, categories, userId)).thenReturn(List.<Object[]>of(retrievedObject));
        List<ItemDTO> itemDTOs = itemService.findItemsUpForFeaturedAuctionBySearchTermAndCategory(searchTerm, categories, userId);
        assertFalse(itemDTOs.isEmpty());
    }

    @Test
    public void testCaseCE1_CE4_CE7() {
        String searchTerm       = "Bicicletta";
        int userId              = 2;
        List<String> categories = new ArrayList<>();

        User owner = new User();
        owner.setUserId(userId);
        Object[] retrievedObject = new Object[] {
                3,
                "Bicicletta",
                "Bicicletta utilizzata di mio nipote",
                "Collezionismo",
                0.0f,
                owner
        };

        when(itemRepository.findFeaturedItemsBySearchTermAndCategory(searchTerm, categories, userId)).thenReturn(List.<Object[]>of(retrievedObject));
        List<ItemDTO> itemDTOs = itemService.findItemsUpForFeaturedAuctionBySearchTermAndCategory(searchTerm, categories, userId);
        assertFalse(itemDTOs.isEmpty());
    }

    @Test
    public void testCaseCE1_CE4_CE8() {
        String searchTerm       = "Bicicletta";
        int userId              = 2;
        List<String> categories = null;

        User owner = new User();
        owner.setUserId(userId);
        Object[] retrievedObject = new Object[] {
                3,
                "Bicicletta",
                "Bicicletta utilizzata di mio nipote",
                "Collezionismo",
                0.0f,
                owner
        };

        when(itemRepository.findFeaturedItemsBySearchTermAndCategory(searchTerm, categories, userId)).thenReturn(List.<Object[]>of(retrievedObject));
        List<ItemDTO> itemDTOs = itemService.findItemsUpForFeaturedAuctionBySearchTermAndCategory(searchTerm, categories, userId);
        assertFalse(itemDTOs.isEmpty());
    }

    @Test
    public void testCaseCE1_CE5_CE7() {
        String searchTerm       = "Bicicletta";
        int userId              = -1;
        List<String> categories = new ArrayList<>();

        List<ItemDTO> items = itemService.findItemsUpForFeaturedAuctionBySearchTermAndCategory(searchTerm, categories, userId);
        assertTrue(items.isEmpty());
    }

    @Test
    public void testCaseCE2_CE4_CE8() {
        String searchTerm       = null;
        int userId              = 2;
        List<String> categories = null;

        List<ItemDTO> items = itemService.findItemsUpForFeaturedAuctionBySearchTermAndCategory(searchTerm, categories, userId);
        assertTrue(items.isEmpty());
    }

    @Test
    public void testCaseCE3_CE4_CE6() {
        String searchTerm       = "    ";
        int userId              = 2;
        List<String> categories = new ArrayList<>();
        categories.add("Collezionismo");

        List<ItemDTO> items = itemService.findItemsUpForFeaturedAuctionBySearchTermAndCategory(searchTerm, categories, userId);
        assertTrue(items.isEmpty());
    }
}