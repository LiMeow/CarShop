package net.thumbtack.shop;

import net.thumbtack.shop.models.User;
import net.thumbtack.shop.models.UserRole;
import net.thumbtack.shop.repositories.UserRepository;
import net.thumbtack.shop.requests.AuthRequest;
import net.thumbtack.shop.services.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTests {
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

        when(userRepository.save(manager)).thenReturn(manager);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());

        assertEquals(manager, authService.signUp(request));
        verify(userRepository).save(manager);
        verify(passwordEncoder).encode(request.getPassword());
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
