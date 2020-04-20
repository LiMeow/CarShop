package net.thumbtack.bank.repositories;

import net.thumbtack.bank.models.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {

    @Query("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber")
    Card findByCardNumber(@Param("cardNumber") String cardNumber);
}
