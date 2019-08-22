package net.thumbtack.bank.exceptions;

public class ErrorResponseItem {
    private ErrorCode errorCode;
    private String message;

    public ErrorResponseItem() {
    }

    public ErrorResponseItem(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ErrorResponseItem(BankException ex) {
        this.errorCode = ex.getErrorCode();
        this.message = ex.getMessage();
    }

    public ErrorResponseItem(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
