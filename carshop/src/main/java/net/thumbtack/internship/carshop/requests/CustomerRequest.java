package net.thumbtack.internship.carshop.requests;

import javax.validation.constraints.NotNull;

public class CustomerRequest {
    @NotNull
    private String name;
    @NotNull
    private String phone;
    private String email;

    public CustomerRequest() { }

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
