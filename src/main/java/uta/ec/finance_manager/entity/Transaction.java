package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;

/**
 * Entidad que representa una transacción financiera en el sistema.
 * <p>
 * Una transacción puede ser de tipo ingreso o egreso, pertenece a una categoría
 * específica y está asociada a una cuenta y un usuario. Incluye información
 * detallada como monto, fecha y descripción.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    /**
     * Identificador único de la transacción.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Monto de la transacción en la moneda del sistema.
     */
    private Double amount;
    
    /**
     * Tipo de transacción (ingreso o egreso).
     */
    private String type;
    
    /**
     * Fecha y hora en que se realizó la transacción.
     */
    private Date date;
    
    /**
     * Descripción detallada de la transacción.
     */
    private String description;

    /**
     * Categoría a la que pertenece la transacción.
     * @see TransactionCategory
     */
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    /**
     * Cuenta en la que se realizó la transacción.
     * Relación muchos a uno con la entidad Account.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * Usuario que realizó la transacción.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
