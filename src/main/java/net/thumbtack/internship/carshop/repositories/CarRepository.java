package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
}
