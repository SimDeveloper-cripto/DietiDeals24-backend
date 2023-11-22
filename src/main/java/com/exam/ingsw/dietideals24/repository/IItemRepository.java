package com.exam.ingsw.dietideals24.repository;

import com.exam.ingsw.dietideals24.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IItemRepository extends CrudRepository<Item, Integer> {

}