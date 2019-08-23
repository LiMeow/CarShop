package net.thumbtack.shop.services;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Customer;
import net.thumbtack.shop.models.User;
import net.thumbtack.shop.repositories.CustomerRepository;
import net.thumbtack.shop.repositories.UserRepository;
import net.thumbtack.shop.requests.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(CustomerRepository customerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(AuthRequest request) {
        LOGGER.debug("AuthService signUp Manager {}", request);

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getUserRole());

        if (userRepository.findByUsername(request.getUsername()) != null) {
            LOGGER.error("Can't signUp User  {}", user);
            throw new CarShopException(ErrorCode.USER_ALREADY_EXISTS, request.getUsername());
        }
        if (request.getCustomerId() != 0) {
            Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);

            if (customer == null)
                throw new CarShopException(ErrorCode.CUSTOMER_NOT_EXISTS, String.valueOf(request.getCustomerId()));

            customer.setUser(user);
            customerRepository.save(customer);
        }
        userRepository.save(user);
        return user;
    }

    public User signIn(AuthRequest request) {
        LOGGER.debug("AuthService signIn Manager {}", request);

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            LOGGER.error("Unable to signIn: User with username '{}' not exists.", user.getUsername());
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, request.getUsername());
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            LOGGER.error("Unable to signIn: User with username '{}' entered the wrong password ", user.getUsername());
            throw new CarShopException(ErrorCode.WRONG_PASSWORD);
        }

        return user;
    }
}
