package net.thumbtack.shop.repositories;

import net.thumbtack.shop.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.userRole = 'ROLE_MANAGER' ")
    User findManagerByUsername(@Param("username") String username);
}
