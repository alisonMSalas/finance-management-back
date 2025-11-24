package uta.ec.finance_manager.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entidad que representa una inversión financiera en el sistema.
 * <p>
 * Una inversión permite al usuario registrar y hacer seguimiento de sus
 * inversiones, incluyendo el monto inicial, la tasa de retorno esperada
 * y las fechas de inicio y finalización de la inversión.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "investment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investment {
    /**
     * Identificador único de la inversión.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Nombre descriptivo de la inversión (ej: "Fondos de Inversión", "Acciones Tech").
     */
    private String name;
    
    /**
     * Monto inicial invertido.
     */
    private Double initialAmount;
    
    /**
     * Tasa de retorno esperada de la inversión (expresada como decimal, ej: 0.05 para 5%).
     */
    private Double returnRate;
    
    /**
     * Fecha de inicio de la inversión.
     */
    private Date startDate;
    
    /**
     * Fecha de finalización o maduración de la inversión.
     */
    private Date endDate;

    /**
     * Usuario propietario de la inversión.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
