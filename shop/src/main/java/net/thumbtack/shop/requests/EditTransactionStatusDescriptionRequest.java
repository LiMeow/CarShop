package net.thumbtack.shop.requests;

import javax.validation.constraints.NotNull;

public class EditTransactionStatusDescriptionRequest {
    @NotNull
    private String description;

    public EditTransactionStatusDescriptionRequest() {
    }

    public EditTransactionStatusDescriptionRequest(@NotNull String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
