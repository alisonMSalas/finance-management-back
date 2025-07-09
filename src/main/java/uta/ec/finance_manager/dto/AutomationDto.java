package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.Frequency;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;

@Getter
@Setter
public class AutomationDto {
    private Integer id;

    @NotNull(message = "El monto es obligatorio")
    private Double amount;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "La frecuencia es obligatoria")
    private Frequency frequency;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date startDate;

    @NotNull(message = "La categoría es obligatoria")
    private TransactionCategory category;

    private Integer userId;
    private Date lastExecutionDate;

    @NotNull(message = "La cuenta es obligatoria")
    private Integer accountId;
}
