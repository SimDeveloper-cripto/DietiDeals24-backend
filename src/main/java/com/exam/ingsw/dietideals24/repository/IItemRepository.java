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
        - Only for active auctions
        - Items that have not been auctioned by the user and that have never bid on by him
    **/
    @Query("SELECT DISTINCT i.itemId, i.name, i.description, i.category, i.basePrize, i.user  FROM Item i " +
            "LEFT JOIN i.auction a " +
            "LEFT JOIN a.offers o " +
            "WHERE " +
            "(i.user.userId <> :userId OR i.user.userId IS NULL) AND " +
            "(o.user.userId <> :userId OR o.user.userId IS NULL) AND " +
            "(:categories IS NULL OR i.category IN :categories) AND " +
            "(:searchTerm IS NULL OR i.name LIKE %:searchTerm% OR i.description LIKE %:searchTerm%) AND " +
            "(a.active = true OR a.active IS NULL)") // a.active IS NULL is necessary because of the left-join
    List<Object[]> findItemsForFeaturedAuction(
            @Param("searchTerm") String searchTerm,
            @Param("categories") List<String> categories,
            @Param("userId") Integer userId); // Given: searchTerm, categories, userId

    /* [CREATED BY USER ITEMS UP FOR AUCTION]
        - Only for active auctions
        - Only items that have been auctioned by the user
    **/
    @Query("SELECT DISTINCT i.itemId, i.name, i.description, i.category, i.basePrize, i.user  FROM Item i " +
            "LEFT JOIN i.auction a " +
            "LEFT JOIN a.offers o " +
            "WHERE i.user.userId = :userId AND i.user.email = :email AND (o.user IS NULL OR o.user.userId <> :userId) AND " +
            "(a.active = true OR a.active IS NULL)")
    List<Object[]> findCreatedByUserItems(
            @Param("userId") Integer userId,
            @Param("email") String email); // Given: userId, email (which is unique)

    /* [ITEMS FOR WICH THE USER PARTECIPATES IN AN AUCTION]
        - Only for active auctions
        - Items for which the user is auctioning (he did not create the auction for it)
    **/
    @Query("SELECT DISTINCT i FROM Item i " +
            "JOIN i.auction a " +
            "JOIN a.offers o " +
            "WHERE o.user.userId = :userId AND o.user.email = :email AND o.user.password = :password " +
            "AND a.active = true AND i.user.userId <> :userId")
    List<Object[]> findItemsForUser(
            @Param("userId") Integer userId,
            @Param("email") String email,
            @Param("password") String password); // Given: userId, email (which is unique), password

    @Query("SELECT i.image FROM Item i " +
            "WHERE i.itemId = :itemId AND i.name = :name")
    byte[] findImageByIdAndName(
             @Param("itemId") Integer itemId,
             @Param("name") String name);
}