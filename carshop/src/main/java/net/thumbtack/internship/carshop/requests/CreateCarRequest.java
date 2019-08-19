package net.thumbtack.internship.carshop.requests;

import javax.validation.constraints.NotNull;

public class CreateCarRequest {
    @NotNull
    private String picture;
    @NotNull
    private String model;
    @NotNull
    private int price;
    @NotNull
    private int production;

    public CreateCarRequest() {}

    public String getPicture() {
        return picture;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getProduction() {
        return production;
    }
}
