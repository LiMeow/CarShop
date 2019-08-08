package net.thumbtack.internship.carshop.models;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp date;

    @Column(name = "status_name")
    @Enumerated(EnumType.STRING)
    private TransactionStatus statusName;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_request_id")
    private CarRequest carRequest;

    public Status(int id, Timestamp date, TransactionStatus statusName, String description, CarRequest carRequest) {
        this.id = id;
        this.date = date;
        this.statusName = statusName;
        this.description = description;
        this.carRequest = carRequest;
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

    public TransactionStatus getStatusName() {
        return statusName;
    }

    public void setStatusName(TransactionStatus statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CarRequest getCarRequest() {
        return carRequest;
    }

    public void setCarRequest(CarRequest carRequest) {
        this.carRequest = carRequest;
    }
}
