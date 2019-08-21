package net.thumbtack.shop.requests;

import net.thumbtack.shop.models.StatusName;

import javax.validation.constraints.NotNull;

public class AddTransactionStatusRequest {
    @NotNull
    private StatusName statusName;

    public AddTransactionStatusRequest() {
    }

    public AddTransactionStatusRequest(@NotNull StatusName statusName) {
        this.statusName = statusName;
    }

    public StatusName getStatusName() {
        return statusName;
    }
}
