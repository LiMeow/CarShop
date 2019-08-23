package net.thumbtack.shop.repositories;

import net.thumbtack.shop.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.user.username = :username AND c.user.userRole = 'ROLE_CUSTOMER' ")
    Customer findCustomerByUsername(@Param("username") String username);
}
