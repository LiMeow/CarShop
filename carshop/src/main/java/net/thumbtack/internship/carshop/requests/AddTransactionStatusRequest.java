package net.thumbtack.internship.carshop.requests;

import net.thumbtack.internship.carshop.models.StatusName;

import javax.validation.constraints.NotNull;

public class AddTransactionStatusRequest {
    @NotNull
    private StatusName statusName;

    public AddTransactionStatusRequest() { }

    public StatusName getStatusName() {
        return statusName;
    }
}
