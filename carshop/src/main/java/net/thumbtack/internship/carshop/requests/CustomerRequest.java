package net.thumbtack.internship.carshop.requests;

import javax.validation.constraints.NotNull;

public class CustomerRequest {
    @NotNull
    private String name;
    private String phone;
    private String email;

    public CustomerRequest(@NotNull String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
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
}
