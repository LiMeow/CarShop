package net.thumbtack.bank.exceptions;

public enum ErrorCode {

    CARD_NOT_EXISTS("Card with number '%s' not exists."),
    INVALID_CARD_HOLDER_NAME(" Invalid card holder name"),
    INVALID_CVV(" Invalid cvc/cvv"),
    INVALID_EXPIRATION_DATE(" Invalid expiration date"),
    INTERNAL_SERVER_ERROR("'%s'"),
    NOT_ENOUGH_MONEY("Not enough money"),
    WRONG_OR_EMPTY_REQUEST("Wrong or empty request"),
    WRONG_URL("Wrong URL"),
    WRONG_PASSWORD("Wrong password."),
    USER_ALREADY_EXISTS("User with username '%s' already exists."),
    USER_NOT_EXISTS("User with username '%s' not exists.");


    private String message;

    ErrorCode() {
    }

    private ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
