package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
