package net.thumbtack.bank.repositories;

import net.thumbtack.bank.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {

    Card findByCardNumber(String cardNumber);
}
