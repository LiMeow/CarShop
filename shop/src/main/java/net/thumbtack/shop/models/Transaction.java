package net.thumbtack.shop.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="car_id")
    private Car car;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="manager_id")
    private User manager;

    public Transaction() {
    }

    public Transaction(int id, Car car, Customer customer, User manager) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.manager = manager;
    }

    public Transaction(int id, Car car, Customer customer) {
        this(id, car, customer, null);
    }

    public Transaction(Car car, Customer customer) {
        this(0, car, customer);
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getId() == that.getId() &&
                getCar().equals(that.getCar()) &&
                getCustomer().equals(that.getCustomer()) &&
                Objects.equals(getManager(), that.getManager());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCar(), getCustomer(), getManager());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", car=" + car +
                ", customer=" + customer +
                ", manager=" + manager +
                '}';
    }
}
