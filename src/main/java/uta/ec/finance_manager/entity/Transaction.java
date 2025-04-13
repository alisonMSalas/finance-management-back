package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.CategoryType;

import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private String type;
    private Date date;
    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
