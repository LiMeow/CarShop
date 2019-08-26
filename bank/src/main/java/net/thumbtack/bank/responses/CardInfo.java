package net.thumbtack.bank.responses;

import java.util.Objects;

public class CardInfo {
    private String cardNumber;
    private double deposit;

    public CardInfo(String cardNumber, double deposit) {
        this.cardNumber = cardNumber;
        this.deposit = deposit;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardInfo)) return false;
        CardInfo cardInfo = (CardInfo) o;
        return Double.compare(cardInfo.getDeposit(), getDeposit()) == 0 &&
                getCardNumber().equals(cardInfo.getCardNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getDeposit());
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "cardNumber='" + cardNumber + '\'' +
                ", deposit=" + deposit +
                '}';
    }
}
