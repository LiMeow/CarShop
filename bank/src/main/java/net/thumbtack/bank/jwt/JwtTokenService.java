package net.thumbtack.bank.jwt;


import net.thumbtack.bank.models.Admin;
import net.thumbtack.bank.requests.AuthRequest;
import org.springframework.security.core.Authentication;

import java.time.Duration;

public interface JwtTokenService {
    String JWT_REGEX = "\\w+.\\w+.\\w+";

    Authentication parseToken(String token);

    //String createToken(Manager manager);

    String createToken(Admin admin);

    String createToken(AuthRequest request);

    Duration getTokenExpiredIn();

    String getDataFromToken(String token);

    Duration getLongTokenExpiredIn();
}
