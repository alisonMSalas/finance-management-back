package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;

/**
 * Objeto de transferencia de datos para la entidad Automation.
 * <p>
 * Este DTO se utiliza para transportar información de transacciones automáticas
 * programadas. Permite configurar transacciones recurrentes que se ejecutarán
 * automáticamente según la frecuencia especificada.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Getter
@Setter
public class AutomationDto {
    /**
     * Identificador único de la automatización.
     */
    private Integer id;

    /**
     * Monto de la transacción automática.
     * Campo obligatorio.
     */
    @NotNull(message = "El monto es obligatorio")
    private Double amount;

    /**
     * Frecuencia de ejecución de la automatización (diaria, semanal, mensual, etc.).
     * Campo obligatorio.
     */
    @NotBlank(message = "La frecuencia es obligatoria")
    private String frequency;

    /**
     * Fecha de inicio de la automatización.
     * A partir de esta fecha comenzarán a ejecutarse las transacciones automáticas.
     * Campo obligatorio.
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date startDate;

    /**
     * Categoría de la transacción automática.
     * Campo obligatorio.
     * @see TransactionCategory
     */
    @NotNull(message = "La categoría es obligatoria")
    private TransactionCategory category;

    /**
     * Identificador del usuario propietario de la automatización.
     * Campo obligatorio.
     */
    @NotNull(message = "El usuario es obligatorio")
    private Integer userId;

    /**
     * Identificador de la cuenta asociada a la automatización.
     * Campo obligatorio.
     */
    @NotNull(message = "La cuenta es obligatoria")
    private Integer accountId;
}
