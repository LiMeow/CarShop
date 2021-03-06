package net.thumbtack.shop.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String picture;
    private String model;
    private int price;
    private int production;
    private boolean available;

    public Car() {}

    public Car(int id, String picture, String model, int price, int production, boolean available) {
        this.id = id;
        this.picture = picture;
        this.model = model;
        this.price = price;
        this.production = production;
        this.available = available;
    }

    public Car(String picture, String model, int price, int production) {
        this(0, picture, model, price, production, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return getId() == car.getId() &&
                getPrice() == car.getPrice() &&
                getProduction() == car.getProduction() &&
                isAvailable() == car.isAvailable() &&
                Objects.equals(getPicture(), car.getPicture()) &&
                Objects.equals(getModel(), car.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPicture(), getModel(), getPrice(), getProduction(), isAvailable());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", production=" + production +
                ", available=" + available +
                '}';
    }
}
