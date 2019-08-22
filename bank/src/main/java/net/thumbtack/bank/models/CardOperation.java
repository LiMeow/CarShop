package net.thumbtack.bank.models;

public enum CardOperation {
    PUT_MONEY("Car with id '%s' not exists."),
    TAKE_MONEY("Car with id '%s' not exists.");

    private String description;

    CardOperation() {
    }

    CardOperation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
