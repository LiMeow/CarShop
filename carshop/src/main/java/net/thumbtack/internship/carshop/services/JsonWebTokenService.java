package net.thumbtack.internship.carshop.services;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.thumbtack.internship.carshop.config.JwtSettings;
import net.thumbtack.internship.carshop.jwt.AuthenticatedJwtToken;
import net.thumbtack.internship.carshop.jwt.JwtTokenService;
import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.requests.AuthRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Service
public class JsonWebTokenService implements JwtTokenService {
    private JwtSettings jwtSettings;


    public JsonWebTokenService(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication parseToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token);
        String subject = claims.getBody().getSubject();
        return new AuthenticatedJwtToken(subject, new ArrayList<>());
    }

    @Override
    public String createToken(Manager manager) {
        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuedAt(Date.from(now))
                .setSubject(manager.getUsername())
                .setExpiration(Date.from(now.plus(jwtSettings.getTokenExpiredIn())));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();
    }

    @Override
    public String createToken(AuthRequest request) {
        Instant now = Instant.now();
        Gson gson = new Gson();
        Claims claims = Jwts.claims()
                .setIssuedAt(Date.from(now))
                .setSubject(gson.toJson(request))
                .setExpiration(Date.from(now.plus(jwtSettings.getLongTokenExpiredIn())));

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
        Date now = Date.from(Instant.now());
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(jwtSettings.getTokenSigningKey())
                .parseClaimsJws(token);

        if (now.compareTo(claims.getBody().getExpiration()) > 0)
            return null;

        else return claims.getBody().getSubject();
    }


}
