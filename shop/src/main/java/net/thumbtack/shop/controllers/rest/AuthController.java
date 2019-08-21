package net.thumbtack.shop.controllers.rest;

import net.thumbtack.shop.jwt.JwtTokenService;
import net.thumbtack.shop.models.Manager;
import net.thumbtack.shop.requests.AuthRequest;
import net.thumbtack.shop.services.AuthService;
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
                                    HttpServletResponse  response) {
        Manager manager = authService.signUp(request);
        String token = jwtTokenService.createToken(manager);
        Cookie cookie=new Cookie("accessToken",token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis()/1000));
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(path = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest request,
                                    HttpServletResponse  response) {
        Manager manager = authService.signIn(request);
        String token = jwtTokenService.createToken(manager);
        Cookie cookie=new Cookie("accessToken",token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis()/1000));
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }



}
