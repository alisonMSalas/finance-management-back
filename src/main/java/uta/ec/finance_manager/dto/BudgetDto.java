package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.TransactionCategory;
import uta.ec.finance_manager.enums.BudgetPeriod;

import java.util.Date;

/**
 * Objeto de transferencia de datos para la entidad Budget.
 * <p>
 * Este DTO se utiliza para transportar información de presupuestos
 * entre las capas de la aplicación. Permite definir límites de gasto
 * por categoría y período, con seguimiento del monto actual gastado.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    /**
     * Identificador único del presupuesto.
     */
    private Integer id;

    /**
     * Monto máximo permitido para este presupuesto.
     * Campo obligatorio.
     */
    @NotNull(message = "El monto máximo es obligatorio")
    private Double maxAmount;
    
    /**
     * Monto actualmente gastado en este presupuesto.
     * Se actualiza automáticamente con cada transacción.
     */
    private Double currentAmount;

    /**
     * Período de tiempo del presupuesto (semanal, mensual, anual).
     * Campo obligatorio.
     * @see BudgetPeriod
     */
    @NotNull(message = "El periodo es obligatorio")
    private BudgetPeriod period;

    /**
     * Categoría de transacción a la que aplica este presupuesto.
     * Campo obligatorio.
     * @see TransactionCategory
     */
    @NotNull(message = "La categoría es obligatoria")
    private TransactionCategory category;
    
    /**
     * Identificador del usuario propietario del presupuesto.
     */
    private Integer userId;
}
