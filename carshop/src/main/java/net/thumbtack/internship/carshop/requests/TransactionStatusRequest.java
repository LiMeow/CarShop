package net.thumbtack.internship.carshop.requests;

import net.thumbtack.internship.carshop.models.StatusName;

import javax.validation.constraints.NotNull;

public class TransactionStatusRequest {
    @NotNull
    private StatusName statusName;

    public TransactionStatusRequest() {
    }

    public StatusName getStatusName() {
        return statusName;
    }
}
