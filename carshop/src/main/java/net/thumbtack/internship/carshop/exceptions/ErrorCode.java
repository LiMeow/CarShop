package net.thumbtack.internship.carshop.exceptions;

public enum ErrorCode {

    EMPTY_REQUEST_BODY("json", "Empty request body."),
    INTERNAL_SERVER_ERROR("", "'%s'"),
    USER_ALREADY_EXISTS("email", "User with username '%s' already exists."),
    USER_NOT_FOUND("id", "User with username '%s' not found."),
    WRONG_OR_EMPTY_REQUEST("json", "Wrong or empty request"),
    WRONG_PASSWORD("password", "Wrong password."),
    WRONG_URL("url", "Wrong URL");


    private String field;
    private String message;

    ErrorCode() {
    }

    private ErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
