package uta.ec.finance_manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;

/**
 * Objeto de transferencia de datos para la entidad Transaction.
 * <p>
 * Este DTO se utiliza para transportar información de transacciones financieras
 * entre las capas de la aplicación. Incluye validaciones para asegurar la
 * integridad de los datos de las transacciones.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Getter
@Setter
public class TransactionDto {
    /**
     * Identificador único de la transacción.
     */
    private Integer id;

    /**
     * Monto de la transacción.
     * Campo obligatorio.
     */
    @NotNull(message = "La cantidad es obligatoria")
    private Double amount;

    /**
     * Tipo de transacción (ingreso o egreso).
     * Campo obligatorio.
     */
    @NotNull(message = "El tipo es obligatorio")
    private String type;

    /**
     * Fecha en que se realizó la transacción.
     * Formato: yyyy-MM-dd, Zona horaria: America/Guayaquil.
     * Campo obligatorio.
     */
    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Guayaquil")
    private Date date;

    /**
     * Identificador de la cuenta asociada a esta transacción.
     * Campo obligatorio.
     */
    @NotNull(message = "La cuenta es obligatoria")
    private Integer accountId;

    /**
     * Categoría de la transacción.
     * Campo obligatorio.
     * @see TransactionCategory
     */
    @NotNull(message = "La categoria es obligatoria")
    private TransactionCategory category;

    /**
     * Identificador del usuario que realizó la transacción.
     */
    private Integer userId;
    
    /**
     * Descripción opcional de la transacción.
     */
    private String description;
}
