package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.Manager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    @Query("SELECT m FROM Manager m WHERE m.username = :username ")
    public Manager findByUsername(@Param("username") String username);
}
