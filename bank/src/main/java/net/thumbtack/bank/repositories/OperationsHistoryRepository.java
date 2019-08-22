package net.thumbtack.bank.repositories;

import net.thumbtack.bank.models.OperationsHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsHistoryRepository extends CrudRepository<OperationsHistory, Integer> {
}
