package net.thumbtack.bank.models;

public enum CardOperation {
    CARD_CREATED("Card with number '%s' has been created."),
    CARD_DELETED("Car with number '%s' has been deleted."),
    PUT_MONEY("+'%s'"),
    TAKE_MONEY("-'%s'");

    private String message;

    CardOperation() {
    }

    CardOperation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

