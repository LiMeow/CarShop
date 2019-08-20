package net.thumbtack.internship.carshop.jwt;

import net.thumbtack.internship.carshop.exceptions.JwtAuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProvider  implements AuthenticationProvider {
    private JwtTokenService jwtTokenService;

    public JwtAuthenticationProvider(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = String.valueOf(authentication.getCredentials());

        try {
            return jwtTokenService.parseToken(token);
        } catch (Exception e) {
            throw new JwtAuthenticationException("Invalid token received", e);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (JwtToken.class.isAssignableFrom(authentication));
    }

}
