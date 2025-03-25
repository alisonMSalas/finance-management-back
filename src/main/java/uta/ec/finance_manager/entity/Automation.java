package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.Enum.Category;

import java.util.Date;

@Entity
@Table(name = "automation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Automation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private String frequency;
    private Date startDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
