package net.thumbtack.bank.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card card;
    private String operation;
    private Timestamp date;

    public Operation() {
    }

    public Operation(int id, Card card, String operation, Timestamp date) {
        this.id = id;
        this.card = card;
        this.operation = operation;
        this.date = date;
    }

    public Operation(Card card, String operation) {
        this(0, card, operation, Timestamp.from(Instant.now()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation operation1 = (Operation) o;
        return getId() == operation1.getId() &&
                getCard().equals(operation1.getCard()) &&
                getOperation().equals(operation1.getOperation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCard(), getOperation(), getDate());
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", card=" + card +
                ", operation='" + operation + '\'' +
                ", date=" + date +
                '}';
    }
}
