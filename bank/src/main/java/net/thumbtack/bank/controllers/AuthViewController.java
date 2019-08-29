package net.thumbtack.bank.controllers;


import net.thumbtack.bank.jwt.JwtTokenService;
import net.thumbtack.bank.models.Admin;
import net.thumbtack.bank.requests.AuthRequest;
import net.thumbtack.bank.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthViewController {
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthViewController(AuthService authService, JwtTokenService jwtTokenService) {
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/signin")
    public String signInPage() {
        return "signin";
    }

    @PostMapping("/sgnIn")
    public String signIn(@ModelAttribute("authRequest") AuthRequest authRequest, HttpServletResponse response) {

        Admin admin = authService.signIn(authRequest);
        String token = jwtTokenService.createToken(admin);
        Cookie cookie = new Cookie("bankAccessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String managerSignUpPage() {
        return "signup";
    }

    @PostMapping("/sgnUp")
    public String signUp(@ModelAttribute("authRequest") AuthRequest authRequest, HttpServletResponse response) {

        Admin admin = authService.signUp(authRequest);
        String token = jwtTokenService.createToken(admin);
        Cookie cookie = new Cookie("bankAccessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return "redirect:/";
    }

}
