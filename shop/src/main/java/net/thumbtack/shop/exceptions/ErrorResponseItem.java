package net.thumbtack.shop.exceptions;

public class ErrorResponseItem {
    private ErrorCode errorCode;
    private String field;
    private String message;

    public ErrorResponseItem() {
    }

    public ErrorResponseItem(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.field = errorCode.getField();
        this.message = errorCode.getMessage();
    }

    public ErrorResponseItem(CarShopException ex) {
        this.errorCode = ex.getErrorCode();
        this.field = ex.getErrorCode().getField();
        this.message = ex.getMessage();
    }

    public ErrorResponseItem(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.field = errorCode.getField();
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
