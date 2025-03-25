package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
