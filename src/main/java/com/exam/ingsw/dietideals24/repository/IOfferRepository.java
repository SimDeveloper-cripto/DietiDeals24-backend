package com.exam.ingsw.dietideals24.repository;

import java.util.List;
import java.util.Optional;

import com.exam.ingsw.dietideals24.model.Offer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IOfferRepository extends CrudRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o WHERE o.auction.auctionId = :auctionId AND o.auction.item.itemId = :itemId ORDER BY o.offer DESC, o.offerDate DESC, o.offerTime DESC")
    Offer findTopOfferByItemIdAndAuctionId(Integer itemId, Integer auctionId);

    Optional<Offer> findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(Integer auctionId);

    @Query("SELECT o FROM Offer o WHERE o.auction.auctionId = :auctionId AND o.auction.item.itemId = :itemId")
    List<Offer> findOffers(Integer itemId, Integer auctionId);

    @Query("SELECT o FROM Offer o WHERE o.user.userId <> o.auction.winnerId")
    List<Offer> findOffersWhereUserIdIsNotWinner();
}