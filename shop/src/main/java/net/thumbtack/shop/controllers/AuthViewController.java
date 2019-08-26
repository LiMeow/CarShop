package net.thumbtack.shop.controllers;


import net.thumbtack.shop.jwt.JwtTokenService;
import net.thumbtack.shop.models.User;
import net.thumbtack.shop.models.UserRole;
import net.thumbtack.shop.requests.AuthRequest;
import net.thumbtack.shop.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

        User user = authService.signIn(authRequest);
        String token = jwtTokenService.createToken(user);
        Cookie cookie = new Cookie("accessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/manager/signup")
    public String managerSignUpPage(Model model) {

        model.addAttribute("userRole", UserRole.ROLE_MANAGER);
        return "signup";
    }

    @GetMapping("/customer/{id}/signup")
    public String CustomerSignUpPage(@PathVariable("id") int customerId, Model model) {

        model.addAttribute("userRole", UserRole.ROLE_CUSTOMER);
        model.addAttribute("customerId", customerId);
        return "signup";
    }

    @PostMapping("/sgnUp")
    public String signUp(@ModelAttribute("authRequest") AuthRequest authRequest, HttpServletResponse response) {

        User user = authService.signUp(authRequest);
        String token = jwtTokenService.createToken(user);
        Cookie cookie = new Cookie("accessToken", token);

        cookie.isHttpOnly();
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return "redirect:/";
    }

}
