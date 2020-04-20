package net.thumbtack.shop;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Customer;
import net.thumbtack.shop.models.User;
import net.thumbtack.shop.models.UserRole;
import net.thumbtack.shop.repositories.CustomerRepository;
import net.thumbtack.shop.repositories.UserRepository;
import net.thumbtack.shop.requests.AuthRequest;
import net.thumbtack.shop.services.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTests {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @Test
    public void testSignUp() {
        AuthRequest request = new AuthRequest("manager", "password");
        User manager = new User(request.getUsername(), request.getPassword(), UserRole.ROLE_MANAGER);

        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRepository.findByUsername(manager.getUsername())).thenReturn(null);
        when(userRepository.save(manager)).thenReturn(manager);

        assertEquals(manager, authService.signUp(request));

        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).findByUsername(manager.getUsername());
        verify(userRepository).save(manager);
    }

    @Test
    public void testSignUpAlreadyExistingUser() {
        AuthRequest request = new AuthRequest("manager", "password");
        User manager = new User(1, request.getUsername(), request.getPassword(), UserRole.ROLE_MANAGER);

        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRepository.findByUsername(manager.getUsername())).thenReturn(manager);

        try {
            assertEquals(manager, authService.signUp(request));
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_ALREADY_EXISTS, ex.getErrorCode());
        }

        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).findByUsername(manager.getUsername());
    }

    @Test
    public void testSignUpCustomer() {
        AuthRequest request = new AuthRequest("customer", "password", UserRole.ROLE_CUSTOMER, 1);
        Customer customer = new Customer(1, "Name", "+12345678997");
        User user = new User(request.getUsername(), request.getPassword(), UserRole.ROLE_CUSTOMER);
        Customer updatedCustomer = new Customer(1, user, "Name", "+12345678997");

        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(customer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, authService.signUp(request));

        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).findByUsername(user.getUsername());
        verify(customerRepository).findById(1);
        verify(customerRepository).save(updatedCustomer);
        verify(userRepository).save(user);
    }

    @Test
    public void testSignUpNonExistingCustomer() {
        AuthRequest request = new AuthRequest("customer", "password", UserRole.ROLE_CUSTOMER, 1);
        User user = new User(request.getUsername(), request.getPassword(), UserRole.ROLE_CUSTOMER);

        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        try {
            authService.signUp(request);
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.CUSTOMER_NOT_EXISTS, ex.getErrorCode());
        }

        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).findByUsername(user.getUsername());
        verify(customerRepository).findById(1);
    }

    @Test
    public void testSignIn() {
        AuthRequest request = new AuthRequest("manager", "password");
        User manager = new User(request.getUsername(), request.getPassword(), UserRole.ROLE_MANAGER);

        when(userRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(passwordEncoder.matches(request.getPassword(), manager.getPassword())).thenReturn(true);

        assertEquals(manager, authService.signIn(request));
        verify(userRepository).findByUsername(manager.getUsername());
        verify(passwordEncoder).matches(request.getPassword(), manager.getPassword());
    }
}
