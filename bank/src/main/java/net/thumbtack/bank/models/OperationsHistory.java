package net.thumbtack.bank.models;

import javax.persistence.*;

@Entity
@Table(name = "operations_history")
public class OperationsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card card;
    private String validity;
}
