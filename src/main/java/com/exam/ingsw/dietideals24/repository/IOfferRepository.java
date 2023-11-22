package com.exam.ingsw.dietideals24.repository;

import com.exam.ingsw.dietideals24.model.Offer;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IOfferRepository extends CrudRepository<Offer, Long>{

}