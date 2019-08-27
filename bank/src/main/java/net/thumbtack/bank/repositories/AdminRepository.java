package net.thumbtack.bank.repositories;

import net.thumbtack.bank.models.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {

    Admin findByUsername(String username);
}
