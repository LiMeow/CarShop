package net.thumbtack.internship.carshop.jwt;

import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.requests.AuthRequest;
import org.springframework.security.core.Authentication;

import java.time.Duration;

public interface JwtTokenService {
    String JWT_REGEX = "\\w+.\\w+.\\w+";

    Authentication parseToken(String token);

    String createToken(Manager manager);

    String createToken(AuthRequest request);

    Duration getTokenExpiredIn();

    String getDataFromToken(String token);

    Duration getLongTokenExpiredIn();
}
