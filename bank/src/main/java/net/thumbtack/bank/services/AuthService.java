package net.thumbtack.bank.services;

import net.thumbtack.bank.exceptions.BankException;
import net.thumbtack.bank.exceptions.ErrorCode;
import net.thumbtack.bank.models.Admin;
import net.thumbtack.bank.repositories.AdminRepository;
import net.thumbtack.bank.requests.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin signUp(AuthRequest request) {
        LOGGER.debug("AuthService signUp Admin {}", request);

        Admin admin = new Admin(request.getUsername(), passwordEncoder.encode(request.getPassword()));

        if (adminRepository.findByUsername(request.getUsername()) != null) {
            LOGGER.error("Can't signUp User  {}", admin);
            throw new BankException(ErrorCode.USER_ALREADY_EXISTS, request.getUsername());
        }

        adminRepository.save(admin);
        return admin;
    }

    public Admin signIn(AuthRequest request) {
        LOGGER.debug("AuthService signIn Admin {}", request);

        Admin admin = adminRepository.findByUsername(request.getUsername());

        if (admin == null) {
            LOGGER.error("Unable to signIn: Admin with username '{}' not exists.", request.getUsername());
            throw new BankException(ErrorCode.USER_NOT_EXISTS, request.getUsername());
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            LOGGER.error("Unable to signIn: Admin with username '{}' entered the wrong password ", request.getUsername());
            throw new BankException(ErrorCode.WRONG_PASSWORD);
        }

        return admin;
    }
}
