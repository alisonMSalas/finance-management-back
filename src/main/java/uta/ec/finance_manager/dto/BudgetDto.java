package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.TransactionCategory;
import uta.ec.finance_manager.enums.BudgetPeriod;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Integer id;

    @NotNull(message = "El monto máximo es obligatorio")
    private Double maxAmount;
    private Double currentAmount;

    @NotNull(message = "El periodo es obligatorio")
    private BudgetPeriod period;

    @NotNull(message = "La categoría es obligatoria")
    private TransactionCategory category;
    private Integer userId;
}
