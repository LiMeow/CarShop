package net.thumbtack.internship.carshop.controllers;

import net.thumbtack.internship.carshop.jwt.JwtTokenService;
import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.requests.AuthRequest;
import net.thumbtack.internship.carshop.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public String signIn(@ModelAttribute("authRequest") AuthRequest authRequest, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "signin";
        }

        Manager manager = authService.signIn(authRequest);
        String token = jwtTokenService.createToken(manager);
        Cookie cookie = new Cookie("accessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/sgnUp")
    public String signUp(@ModelAttribute("authRequest") AuthRequest authRequest, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        Manager manager = authService.signUp(authRequest);
        String token = jwtTokenService.createToken(manager);
        Cookie cookie = new Cookie("accessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return "redirect:/";
    }

}
