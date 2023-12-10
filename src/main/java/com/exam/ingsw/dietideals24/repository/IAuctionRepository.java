package com.exam.ingsw.dietideals24.repository;

import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IAuctionRepository extends CrudRepository<Auction, Integer> {}