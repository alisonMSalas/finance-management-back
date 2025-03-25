package uta.ec.finance_manager.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "investment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double initialAmount;
    private Double returnRate;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
