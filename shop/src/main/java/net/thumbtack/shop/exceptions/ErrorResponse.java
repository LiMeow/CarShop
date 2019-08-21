package net.thumbtack.shop.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private List<ErrorResponseItem> errors;


    public ErrorResponse() {
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(List<ErrorResponseItem> errors) {
        this.errors = errors;
    }

    public List<ErrorResponseItem> getErrors() {
        return errors;
    }

    public void addError(ErrorResponseItem error) {
        errors.add(error);
    }


}
