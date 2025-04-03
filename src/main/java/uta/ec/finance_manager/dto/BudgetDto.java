package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.CategoryType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Integer id;

    @NotNull(message = "El monto máximo es obligatorio")
    private Double maxAmount;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date startDate;

    @NotNull(message = "La fecha de fin es obligatoria")
    private Date endDate;

    @NotNull(message = "La categoría es obligatoria")
    private CategoryType category;

    @NotNull(message = "El usuario es obligatorio")
    private Integer userId;
}
