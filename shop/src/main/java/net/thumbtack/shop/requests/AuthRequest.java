package net.thumbtack.shop.requests;

import net.thumbtack.shop.models.UserRole;

import javax.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private UserRole userRole;

    private int customerId;

    public AuthRequest() {
    }

    public AuthRequest(@NotNull String username, @NotNull String password, @NotNull UserRole userRole, int customerId) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.customerId = customerId;
    }

    public AuthRequest(@NotNull String username, @NotNull String password) {
        this(username, password, UserRole.ROLE_MANAGER, 0);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userRole +
                '}';
    }
}
