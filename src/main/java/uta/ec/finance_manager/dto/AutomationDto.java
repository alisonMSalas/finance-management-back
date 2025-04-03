package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.CategoryType;

import java.util.Date;

@Getter
@Setter
public class AutomationDto {
    private Integer id;

    @NotNull(message = "El monto es obligatorio")
    private Double amount;

    @NotBlank(message = "La frecuencia es obligatoria")
    private String frequency;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date startDate;

    @NotNull(message = "La categoría es obligatoria")
    private CategoryType category;

    @NotNull(message = "El usuario es obligatorio")
    private Integer userId;

    @NotNull(message = "La cuenta es obligatoria")
    private Integer accountId;
}
