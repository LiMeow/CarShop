package net.thumbtack.bank.controllers.rest;

import net.thumbtack.bank.jwt.JwtTokenService;
import net.thumbtack.bank.models.Admin;
import net.thumbtack.bank.requests.AuthRequest;
import net.thumbtack.bank.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthController(AuthService authService, JwtTokenService jwtTokenService) {
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping(path = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthRequest request,
                                    HttpServletResponse response) {

        Admin admin = authService.signUp(request);
        String token = jwtTokenService.createToken(admin);
        Cookie cookie = new Cookie("bankAccessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);
        return ResponseEntity.ok().body(admin);
    }


    @PostMapping(path = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest request,
                                    HttpServletResponse response) {

        Admin admin = authService.signIn(request);
        String token = jwtTokenService.createToken(admin);
        Cookie cookie = new Cookie("bankAccessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return ResponseEntity.ok().body(admin);
    }


}
