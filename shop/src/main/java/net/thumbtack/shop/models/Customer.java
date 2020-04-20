package net.thumbtack.shop.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String phone;
    private String email;

    public Customer() {
    }

    public Customer(int id, User user, String name, String phone, String email) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String name, String phone, String email) {
        this(0, null, name, phone, email);
    }

    public Customer(int id, String name, String phone) {
        this(id, null, name, phone, null);
    }

    public Customer(int id, User user, String name, String phone) {
        this(id, user, name, phone, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getId() == customer.getId() &&
                Objects.equals(getUser(), customer.getUser()) &&
                getName().equals(customer.getName()) &&
                getPhone().equals(customer.getPhone()) &&
                Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getName(), getPhone(), getEmail());
    }
}
