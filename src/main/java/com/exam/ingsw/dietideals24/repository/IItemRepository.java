package com.exam.ingsw.dietideals24.repository;

import java.util.List;
import com.exam.ingsw.dietideals24.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IItemRepository extends CrudRepository<Item, Integer> {
    @Query("SELECT i FROM Item i " +
            "WHERE LOWER(i.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND " +
            "(:categories IS NULL OR i.category IN :categories)")
    List<Item> findItemsBySearchTermAndCategories(
            @Param("searchTerm") String searchTerm,
            @Param("categories") List<String> categories);
}