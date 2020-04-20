package net.thumbtack.shop.requests;

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

    public CreateCarRequest(@NotNull String picture, @NotNull String model, @NotNull int price, @NotNull int production) {
        this.picture = picture;
        this.model = model;
        this.price = price;
        this.production = production;
    }

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
