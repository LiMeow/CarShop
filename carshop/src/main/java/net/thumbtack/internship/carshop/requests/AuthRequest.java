package net.thumbtack.internship.carshop.requests;

import javax.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
