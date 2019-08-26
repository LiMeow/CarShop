package net.thumbtack.shop.repositories;

import net.thumbtack.shop.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.customer.id = :id")
    Transaction findByCustomerId(@Param("id") int customerId);
}
