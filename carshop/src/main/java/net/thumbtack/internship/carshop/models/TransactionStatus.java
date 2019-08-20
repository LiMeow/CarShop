package net.thumbtack.internship.carshop.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "transaction_status")
public class TransactionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp date;

    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private StatusName statusName;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public TransactionStatus() {
    }

    public TransactionStatus(int id, Timestamp date, StatusName statusName, String description, Transaction transaction) {
        this.id = id;
        this.date = date;
        this.statusName = statusName;
        this.description = description;
        this.transaction = transaction;
    }

    public TransactionStatus(StatusName statusName, Transaction transaction) {
        this(0, Timestamp.from(Instant.now()), statusName, statusName.getDescription(), transaction);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public StatusName getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusName statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
