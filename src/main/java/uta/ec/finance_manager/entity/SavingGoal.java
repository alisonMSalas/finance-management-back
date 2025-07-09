package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.DepositFrequency;
import uta.ec.finance_manager.enums.GoalStatus;

import java.util.Date;

@Entity
@Table(name = "savings_goal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double targetAmount;
    private Double currentBalance;
    private Date deadline;
    private Date lastDepositDate;
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;

    @Enumerated(EnumType.STRING)
    private DepositFrequency depositFrequency;
    private Integer currentStreak = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
