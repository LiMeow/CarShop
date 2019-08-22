package net.thumbtack.bank.repositories;

import net.thumbtack.bank.models.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardOperationRepository extends CrudRepository<Operation, Integer> {
}
