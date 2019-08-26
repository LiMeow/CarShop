package net.thumbtack.bank.requests;

import javax.validation.constraints.NotNull;

public class TakeMoneyRequest {
    @NotNull
    private String cardNumber;
    @NotNull
    private String validity;
    @NotNull
    private String cvv;
    @NotNull
    private String cardHolderName;
    private double money;

    public TakeMoneyRequest() {
    }

    public TakeMoneyRequest(@NotNull String cardNumber, @NotNull String validity, @NotNull String cvv, @NotNull String cardHolderName, double money) {
        this.cardNumber = cardNumber;
        this.validity = validity;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
        this.money = money;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "TakeMoneyRequest{" +
                "cardNumber='" + cardNumber + '\'' +
                ", validity='" + validity + '\'' +
                ", cvv='" + cvv + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", money=" + money +
                '}';
    }
}
