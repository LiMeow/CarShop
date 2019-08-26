package net.thumbtack.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiry_date")
    private String expiryDate;
    private String cvv;

    @Column(name = "card_holder_name")
    private String cardHolderName;
    private double balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    @JsonIgnore
    private List<Operation> operation;

    public Card() {
    }

    public Card(int id, String cardNumber, String expiryDate, String cvv, String cardHolderName, double balance, List<Operation> operation) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
        this.balance = balance;
        this.operation = operation;
    }

    public Card(int id, String cardHolderName, double balance, List<Operation> operation) {
        this.id = id;
        this.cardNumber = generateCardNumber();
        this.expiryDate = generateValidity();
        this.cvv = generateCvv();
        this.cardHolderName = cardHolderName;
        this.balance = balance;
        this.operation = operation;
    }

    public Card(String cardHolderName) {
        this(0, cardHolderName, 0, new ArrayList<>());

    }

    public Card(String cardHolderName, double balance) {
        this(0, cardHolderName, balance, new ArrayList<>());
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public List<Operation> getOperation() {
        return operation;
    }

    public void setOperation(List<Operation> operation) {
        this.operation = operation;
    }


    public String generateCardNumber() {
        String cardNumber = "";
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            cardNumber += (random.nextInt(8999) + 1000) + " ";
        }
        return cardNumber.substring(0, cardNumber.length() - 1);
    }

    public String generateValidity() {

        LocalDate date = LocalDate.now();
        String validity = date.getDayOfMonth() + "/" + (date.getYear() + 5);

        return validity;
    }

    public String generateCvv() {

        String cvv = "";
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            cvv += random.nextInt(9);
        }
        return cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getId() == card.getId() &&
                Double.compare(card.getBalance(), getBalance()) == 0 &&
                getCardNumber().equals(card.getCardNumber()) &&
                getExpiryDate().equals(card.getExpiryDate()) &&
                getCvv().equals(card.getCvv()) &&
                getCardHolderName().equals(card.getCardHolderName()) &&
                Objects.equals(getOperation(), card.getOperation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCardNumber(), getExpiryDate(), getCvv(), getCardHolderName(), getBalance(), getOperation());
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
