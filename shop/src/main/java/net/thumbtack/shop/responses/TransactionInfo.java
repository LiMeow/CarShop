package net.thumbtack.shop.responses;

import net.thumbtack.shop.models.StatusName;

public class TransactionInfo {
    private int id;
    private String date;
    private String model;
    private int price;
    private String customer;
    private int statusId;
    private StatusName status;
    private String description;

    public TransactionInfo() {
    }

    public TransactionInfo(int id, String date, String model, int price, String customer, int statusId, StatusName status, String description) {
        this.id = id;
        this.date = date;
        this.model = model;
        this.price = price;
        this.customer = customer;
        this.statusId = statusId;
        this.status = status;
        this.description = description;
    }

    public TransactionInfo(int id, String date, String model, int price, String customer) {
        this(id, date, model, price, customer, 0, null, null);
    }

    public TransactionInfo(int id, String date, String model, String customer, int statusId, StatusName status, String description) {
        this(id, date, model, 0, customer, statusId, status, description);
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

    public int getStatusId() {
        return statusId;
    }

    public StatusName getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
