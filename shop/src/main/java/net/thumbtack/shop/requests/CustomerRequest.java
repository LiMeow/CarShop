package net.thumbtack.shop.requests;

import javax.validation.constraints.NotNull;

public class CustomerRequest {
    @NotNull
    private String name;
    @NotNull
    private String phone;
    private String email;

    public CustomerRequest() {
    }

    public CustomerRequest(@NotNull String name, @NotNull String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public CustomerRequest(@NotNull String name, @NotNull String phone) {
        this(name, phone, null);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
