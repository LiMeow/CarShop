package net.thumbtack.bank.requests;

import javax.validation.constraints.NotNull;

public class DeleteCardRequest {
    @NotNull
    private String cardNumber;
    @NotNull
    private String validity;
    @NotNull
    private String cvv;
    @NotNull
    private String cardHolderName;

    public DeleteCardRequest(@NotNull String cardNumber, @NotNull String validity, @NotNull String cvv, @NotNull String cardHolderName) {
        this.cardNumber = cardNumber;
        this.validity = validity;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getValidity() {
        return validity;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }
}
