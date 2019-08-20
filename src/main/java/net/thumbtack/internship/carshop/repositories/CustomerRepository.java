package net.thumbtack.internship.carshop.repositories;

import net.thumbtack.internship.carshop.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
