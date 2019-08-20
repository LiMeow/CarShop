package net.thumbtack.internship.carshop.responses;

import net.thumbtack.internship.carshop.models.StatusName;

public class TransactionInfo {
    private int id;
    private String date;
    private String model;
    private int price;
    private String customer;
    private StatusName status;

    public TransactionInfo() {
    }

    public TransactionInfo(int id, String date, String model, int price, String customer, StatusName status) {
        this.id = id;
        this.date = date;
        this.model = model;
        this.price = price;
        this.customer = customer;
        this.status = status;
    }

    public TransactionInfo(int id, String date, String model, int price, String customer) {
        this(id, date, model, price, customer, null);
    }

    public TransactionInfo(int id, String date, String model, String customer, StatusName status) {
        this(id, date, model, 0, customer, status);
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public String getCustomer() {
        return customer;
    }

    public StatusName getStatus() {
        return status;
    }
}
