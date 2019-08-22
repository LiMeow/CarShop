package net.thumbtack.bank.requests;

import javax.validation.constraints.NotNull;

public class CreateCardRequest {
    @NotNull
    private String cardHolderName;

    public CreateCardRequest() {
    }

    public CreateCardRequest(@NotNull String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
