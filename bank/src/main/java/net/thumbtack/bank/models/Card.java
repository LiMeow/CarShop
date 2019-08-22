package net.thumbtack.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "card_number")
    private String cardNumber;
    private String validity;
    private String cvv;

    @Column(name = "card_holder_name")
    private String cardHolderName;
    private double balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    @JsonIgnore
    private List<OperationsHistory> operationsHistory;

    public Card() {
    }

    public Card(int id, String cardNumber, String validity, String cvv, String cardHolderName, double balance, List<OperationsHistory> operationsHistory) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.validity = validity;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
        this.balance = balance;
        this.operationsHistory = operationsHistory;
    }

    public Card(int id, String cardNumber, String validity, String cvv, String cardHolderName, double balance) {
        this(id, cardNumber, validity, cvv, cardHolderName, balance, new ArrayList<>());
    }

    public Card(String cardNumber, String validity, String cvv, String cardHolderName) {
        this(0, cardNumber, validity, cvv, cardHolderName, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void putMoney(double money) {
        this.balance += money;
    }

    public void takeMoney(double money) {
        this.balance -= money;
    }

    public List<OperationsHistory> getOperationsHistory() {
        return operationsHistory;
    }

    public void setOperationsHistory(List<OperationsHistory> operationsHistory) {
        this.operationsHistory = operationsHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getId() == card.getId() &&
                Double.compare(card.getBalance(), getBalance()) == 0 &&
                getCardNumber().equals(card.getCardNumber()) &&
                getValidity().equals(card.getValidity()) &&
                getCvv().equals(card.getCvv()) &&
                getCardHolderName().equals(card.getCardHolderName()) &&
                Objects.equals(getOperationsHistory(), card.getOperationsHistory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCardNumber(), getValidity(), getCvv(), getCardHolderName(), getBalance(), getOperationsHistory());
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", validity='" + validity + '\'' +
                ", cvv='" + cvv + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", balance=" + balance +
                ", operationsHistory=" + operationsHistory +
                '}';
    }
}
