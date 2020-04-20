package net.thumbtack.shop.jwt;


import net.thumbtack.shop.models.User;
import net.thumbtack.shop.requests.AuthRequest;
import org.springframework.security.core.Authentication;

import java.time.Duration;

public interface JwtTokenService {
    String JWT_REGEX = "\\w+.\\w+.\\w+";

    Authentication parseToken(String token);

    //String createToken(Manager manager);

    String createToken(User user);

    String createToken(AuthRequest request);

    Duration getTokenExpiredIn();

    String getDataFromToken(String token);

    Duration getLongTokenExpiredIn();
}
