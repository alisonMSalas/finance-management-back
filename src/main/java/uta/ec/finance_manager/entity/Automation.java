package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;

/**
 * Entidad que representa una transacción automática programada.
 * <p>
 * Las automatizaciones permiten a los usuarios programar transacciones recurrentes
 * que se ejecutarán automáticamente según la frecuencia especificada
 * (diaria, semanal, mensual, etc.).
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "automation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Automation {
    /**
     * Identificador único de la automatización.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Monto de la transacción automática.
     */
    private Double amount;
    
    /**
     * Frecuencia de ejecución (diaria, semanal, mensual, etc.).
     */
    private String frequency;
    
    /**
     * Fecha de inicio de la automatización.
     */
    private Date startDate;

    /**
     * Categoría de la transacción automática.
     * @see TransactionCategory
     */
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    /**
     * Usuario propietario de la automatización.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Cuenta asociada a la automatización.
     * Relación muchos a uno con la entidad Account.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


}
