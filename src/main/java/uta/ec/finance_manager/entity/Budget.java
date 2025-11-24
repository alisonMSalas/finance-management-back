package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.TransactionCategory;
import uta.ec.finance_manager.enums.BudgetPeriod;

/**
 * Entidad que representa un presupuesto financiero.
 * <p>
 * Un presupuesto permite al usuario establecer límites de gasto para diferentes
 * categorías durante períodos específicos (mensual, semanal, etc.).
 * El sistema realiza seguimiento del monto actual gastado vs el monto máximo permitido.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "budget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    /**
     * Identificador único del presupuesto.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Monto máximo permitido para este presupuesto.
     */
    private Double maxAmount;
    
    /**
     * Período de tiempo del presupuesto (mensual, semanal, etc.).
     * @see BudgetPeriod
     */
    private BudgetPeriod period;
    
    /**
     * Monto actualmente gastado en este presupuesto.
     */
    private Double currentAmount;

    /**
     * Categoría de transacción a la que aplica este presupuesto.
     * @see TransactionCategory
     */
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    /**
     * Usuario propietario del presupuesto.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
