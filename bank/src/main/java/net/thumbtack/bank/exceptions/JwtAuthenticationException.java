package net.thumbtack.bank.exceptions;


import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(final String message, final Throwable t) {
        super(message, t);
    }

    public JwtAuthenticationException(final String message) {
        super(message);
    }

}
