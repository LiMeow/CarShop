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

    @Query(value = "SELECT t1.* FROM transaction_status t1 " +
            "JOIN (SELECT transaction_id, max(date) " +
            "FROM transaction_status " +
            "JOIN transaction " +
            "ON transaction_status.transaction_id = transaction.id " +
            "WHERE transaction.manager_id = :managerId " +
            "GROUP BY transaction_id ) t2 " +
            "ON t1.transaction_id = t2.transaction_id " +
            "AND t1.date=t2.max " +
            "ORDER BY t1.date DESC", nativeQuery = true)
    List<TransactionStatus> findAllLastTransactionsStatusesByManager(@Param("managerId") int managerId);


    @Query("SELECT t FROM TransactionStatus t WHERE t.transaction.id = :transactionId")
    List<TransactionStatus> findAllByTransactionId(@Param("transactionId") int transactionId,
                                                   Sort sort);
}
