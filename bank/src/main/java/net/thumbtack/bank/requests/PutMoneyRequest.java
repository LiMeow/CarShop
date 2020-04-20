package net.thumbtack.bank.requests;

import javax.validation.constraints.NotNull;

public class PutMoneyRequest {
    @NotNull
    private String cardNumber;
    @NotNull
    private double money;

    public PutMoneyRequest() {
    }

    public PutMoneyRequest(@NotNull String cardNumber, @NotNull double money) {
        this.cardNumber = cardNumber;
        this.money = money;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}
