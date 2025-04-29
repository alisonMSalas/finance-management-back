package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uta.ec.finance_manager.enums.DepositFrequency;
import uta.ec.finance_manager.enums.GoalStatus;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavingGoalDto {
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La cantidad objetivo es obligatoria")
    private Double targetAmount;

    @NotBlank(message = "El saldo actual es obligatorio")
    private Double currentBalance;

    @NotNull(message = "La fecha límite es obligatoria")
    private Date deadline;

    @NotNull(message = "La fecha de ultimo deposito es obligatoria")
    private Date lastDepositDate;

    @NotNull(message = "La fecha de creación es obligatoria")
    private Date creationDate;

    private GoalStatus goalStatus;

    @NotNull(message = "La frecuencia de depósito es obligatoria")
    private DepositFrequency depositFrequency;

    private Integer currentStreak;

    private Integer userId;

}
