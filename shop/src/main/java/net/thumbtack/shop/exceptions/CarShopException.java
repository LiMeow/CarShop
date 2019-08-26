package net.thumbtack.shop.exceptions;

public class CarShopException  extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public CarShopException(ErrorCode errorCode, String... params) {
        this.errorCode = errorCode;
        this.message = String.format(errorCode.getMessage(), params);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
