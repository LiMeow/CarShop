package net.thumbtack.bank.exceptions;

public class BankException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public BankException(ErrorCode errorCode, String... params) {
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
