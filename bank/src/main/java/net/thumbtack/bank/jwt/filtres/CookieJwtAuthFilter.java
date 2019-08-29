package net.thumbtack.bank.jwt.filtres;

import net.thumbtack.bank.exceptions.JwtAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieJwtAuthFilter extends JwtAuthFilter {

    public CookieJwtAuthFilter(RequestMatcher matcher) {
        super(matcher);
    }

    @Override
    protected String takeToken(HttpServletRequest request) throws AuthenticationException {
        Cookie cookie = WebUtils.getCookie(request, "bankAccessToken");
        if (cookie != null) return cookie.getValue();
        else throw new JwtAuthenticationException("Invalid 'bankAccessToken' cookie: " + cookie);
    }
}
