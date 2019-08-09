package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query("SELECT c FROM Transaction c WHERE manager=null ")
    List<Transaction> findAllFree(Pageable pageable);
}
