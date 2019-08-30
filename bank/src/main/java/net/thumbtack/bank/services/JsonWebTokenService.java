package net.thumbtack.bank.services;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.thumbtack.bank.config.JwtSettings;
import net.thumbtack.bank.jwt.AuthenticatedJwtToken;
import net.thumbtack.bank.jwt.JwtTokenService;
import net.thumbtack.bank.models.Admin;
import net.thumbtack.bank.requests.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class JsonWebTokenService implements JwtTokenService {
    private JwtSettings jwtSettings;
    private final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenService.class);
    private final String AUTHORITY = "authority";

    public JsonWebTokenService(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication parseToken(String token) {
        LOGGER.debug("JsonWebTokenService parse token '{}'", token);

        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token);

        String subject = claims.getBody().getSubject();
        String tokenAuthority = claims.getBody().get(AUTHORITY, String.class);

        List authorities = Collections.singletonList(new SimpleGrantedAuthority(tokenAuthority));

        return new AuthenticatedJwtToken(subject, authorities);
    }

    @Override
    public String createToken(Admin admin) {
        LOGGER.debug("JsonWebTokenService create token for user '{}'", admin);

        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuedAt(Date.from(now))
                .setSubject(admin.getUsername())
                .setExpiration(Date.from(now.plus(jwtSettings.getTokenExpiredIn())));

        claims.put(AUTHORITY, "ROLE_ADMIN");

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();
    }

    @Override
    public String createToken(AuthRequest request) {
        LOGGER.debug("JsonWebTokenService create token by request '{}'", request);

        Instant now = Instant.now();
        Gson gson = new Gson();

        Claims claims = Jwts.claims()
                .setIssuedAt(Date.from(now))
                .setSubject(gson.toJson(request))
                .setExpiration(Date.from(now.plus(jwtSettings.getLongTokenExpiredIn())));

        claims.put(AUTHORITY, "ROLE_ADMIN");

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();
    }

    @Override
    public Duration getTokenExpiredIn() {
        return jwtSettings.getTokenExpiredIn();
    }

    @Override
    public Duration getLongTokenExpiredIn() {
        return jwtSettings.getLongTokenExpiredIn();
    }

    @Override
    public String getDataFromToken(String token) {
        LOGGER.debug("JsonWebTokenService get data from token '{}'", token);

        Date now = Date.from(Instant.now());
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(jwtSettings.getTokenSigningKey())
                .parseClaimsJws(token);

        if (now.compareTo(claims.getBody().getExpiration()) > 0)
            return null;

        else return claims.getBody().getSubject();
    }


}
