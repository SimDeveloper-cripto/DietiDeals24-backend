package com.exam.ingsw.dietideals24.repository;

import java.util.List;
import com.exam.ingsw.dietideals24.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IItemRepository extends CrudRepository<Item, Integer> {
    /* [FEATURED ITEMS UP FOR AUCTION]
        - Items that have not been auctioned by the user and that have never bid on by him
    **/
    @Query("SELECT DISTINCT i.itemId, i.name, i.description, i.category, i.basePrize, i.user  FROM Item i " +
            "LEFT JOIN i.auction a " +
            "LEFT JOIN a.offers o " +
            "WHERE " +
            "(i.user.userId <> :userId OR i.user.userId IS NULL) AND " +
            "(o.user.userId <> :userId OR o.user.userId IS NULL) AND " +
            "(:categories IS NULL OR i.category IN :categories) AND " +
            "(:searchTerm IS NULL OR i.name LIKE %:searchTerm% OR i.description LIKE %:searchTerm%)")
    List<Object[]> findItemsForFeaturedAuction(
            @Param("searchTerm") String searchTerm,
            @Param("categories") List<String> categories,
            @Param("userId") Integer userId); // Given: searchTerm, categories, userId

    @Query("SELECT i.image FROM Item i " +
            "WHERE i.itemId = :itemId AND i.name = :name")
    byte[] findImageByIdAndName(
             @Param("itemId") Integer itemId,
             @Param("name") String name);
}