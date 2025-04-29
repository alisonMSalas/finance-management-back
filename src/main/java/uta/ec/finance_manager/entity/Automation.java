package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import uta.ec.finance_manager.enums.Frequency;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;

@Entity
@Table(name = "automation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Automation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    private Date startDate;
    private Date lastExecutionDate;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


}
