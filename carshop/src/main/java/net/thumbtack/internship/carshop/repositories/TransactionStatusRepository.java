package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.TransactionStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionStatusRepository extends CrudRepository<TransactionStatus, Integer> {
    @Query("SELECT t FROM TransactionStatus t WHERE t.transaction.manager=null")
    List<TransactionStatus> findAllFree(Pageable pageable);

    @Query(value = "SELECT t FROM TransactionStatus t WHERE t.transaction.id= :transactionId ORDER BY  t.date DESC ")
    List<TransactionStatus> findLastTransactionStatus(@Param("transactionId") int transactionId, Pageable pageable);


    @Query("SELECT t FROM TransactionStatus t WHERE t.transaction.id = :transactionId")
    List<TransactionStatus> findAllByTransactionId(@Param("transactionId") int transactionId,
                                                   Sort sort);
}
