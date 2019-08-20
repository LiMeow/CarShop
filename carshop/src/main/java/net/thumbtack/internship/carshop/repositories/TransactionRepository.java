package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.manager.id= :managerId")
    List<Transaction> findAllByManager(@Param("managerId") int managerId);

}
