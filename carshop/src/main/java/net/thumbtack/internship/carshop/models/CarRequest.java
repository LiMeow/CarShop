package net.thumbtack.internship.carshop.models;


import javax.persistence.*;

@Entity
@Table(name="car_request")
public class CarRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="car_id")
    private Car car;

    @OneToOne
    @JoinColumn(name="request_id")
    private Request request;

    @OneToOne
    @JoinColumn(name="manager_id")
    private Manager manager;

    public CarRequest(int id, Car car, Request request, Manager manager) {
        this.id = id;
        this.car = car;
        this.request = request;
        this.manager = manager;
    }

    public CarRequest(Car car, Request request) {
        this(0, car, request, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
