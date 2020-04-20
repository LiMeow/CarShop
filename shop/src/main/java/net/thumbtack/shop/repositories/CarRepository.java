package net.thumbtack.shop.repositories;

import net.thumbtack.shop.models.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE c.available=true")
    List<Car> findAllAvailable();
}
