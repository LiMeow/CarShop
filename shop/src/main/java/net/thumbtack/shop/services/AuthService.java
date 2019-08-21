package net.thumbtack.shop.services;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Manager;
import net.thumbtack.shop.repositories.ManagerRepository;
import net.thumbtack.shop.requests.AuthRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Manager signUp(AuthRequest request) {
        LOGGER.debug("AuthService signUp Manager {}", request);

        Manager manager = new Manager(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        try {
            managerRepository.save(manager);
        } catch (ConstraintViolationException ex) {
            LOGGER.error("Can't signUp Manager {}, {}", manager, ex);
            throw new CarShopException(ErrorCode.USER_ALREADY_EXISTS, request.getUsername());
        }
        return manager;
    }

    public Manager signIn(AuthRequest request) {
        LOGGER.debug("AuthService signIn Manager {}", request);

        Manager manager = managerRepository.findByUsername(request.getUsername());
        if (manager == null) {
            LOGGER.error("Unable to signIn: manager with username '{}' not exists.", manager.getUsername());
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, request.getUsername());
        }

        if (!passwordEncoder.matches(request.getPassword(), manager.getPassword())) {
            LOGGER.error("Unable to signIn: manager with username '{}' entered the wrong password ", manager.getUsername());
            throw new CarShopException(ErrorCode.WRONG_PASSWORD);
        }

        return manager;
    }
}
