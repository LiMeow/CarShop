package net.thumbtack.internship.carshop.requests;

public class UpdateCarRequest {
    private String picture;
    private String model;
    private int price;
    private int production;
    private Boolean available;

    public UpdateCarRequest() {
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

    public Boolean getAvailable() {
        return available;
    }
}
