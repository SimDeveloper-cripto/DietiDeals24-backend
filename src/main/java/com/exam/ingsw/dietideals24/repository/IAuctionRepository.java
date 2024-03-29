package com.exam.ingsw.dietideals24.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import jakarta.transaction.Transactional;
import com.exam.ingsw.dietideals24.enums.Type;
import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IAuctionRepository extends CrudRepository<Auction, Integer> {
    @Query("SELECT a FROM Auction a WHERE (:itemId IS NULL OR a.item.itemId = :itemId) " +
            "AND (:name IS NULL OR a.item.name = :name) " +
            "AND (:description IS NULL OR a.item.description = :description)")
    Optional<Auction> findByItemIdOrItemNameOrItemDescription(
            @Param("itemId") Integer itemId,
            @Param("name") String name,
            @Param("description") String description
    );

    List<Auction> findByAuctionTypeAndActiveIsTrueAndExpirationDateBefore(Type auctionType, Date expirationDate);

    List<Auction> findByAuctionTypeAndActiveIsTrueAndExpirationTimeBefore(Type auctionType, LocalDateTime expirationTime);

    @Modifying
    @Transactional
    @Query("UPDATE Auction a SET a.active = false WHERE a.auctionId = :auctionId")
    void closeAuction(@Param("auctionId") Integer auctionId);

    @Query("SELECT a.winningBid FROM Auction a WHERE a.item.itemId = :itemId")
    Float findWinningBidByItemId(@Param("itemId") Integer itemId);
}