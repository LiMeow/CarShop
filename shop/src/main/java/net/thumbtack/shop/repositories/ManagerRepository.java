package net.thumbtack.shop.repositories;

import net.thumbtack.shop.models.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, Integer> {

    Manager findByUsername(String username);
}
