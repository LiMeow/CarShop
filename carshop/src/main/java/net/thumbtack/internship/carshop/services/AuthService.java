package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.repositories.ManagerRepository;
import net.thumbtack.internship.carshop.requests.AuthRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private ManagerRepository managerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Manager signUp(AuthRequest request) {

        Manager manager = new Manager(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        try {
            managerRepository.save(manager);
        } catch (ConstraintViolationException ex) {
            throw new CarShopException(ErrorCode.USER_ALREADY_EXISTS, request.getUsername());
        }
        return manager;
    }

    public Manager signIn(AuthRequest request) {
        Manager manager = managerRepository.findByUsername(request.getUsername());

        if (manager == null)
            throw new CarShopException(ErrorCode.USER_NOT_FOUND, request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), manager.getPassword()))
            throw new CarShopException(ErrorCode.WRONG_PASSWORD);

        return manager;
    }
}
