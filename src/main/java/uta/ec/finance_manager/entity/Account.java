package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.AccountType;

import java.util.List;

/**
 * Entidad que representa una cuenta financiera en el sistema.
 * <p>
 * Una cuenta puede ser de diferentes tipos (bancaria, efectivo, tarjeta de crédito, etc.)
 * y mantiene un balance actual. Cada cuenta pertenece a un usuario y puede tener
 * múltiples transacciones y automatizaciones asociadas.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     * Identificador único de la cuenta.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Nombre descriptivo de la cuenta (ej: "Cuenta de Ahorros", "Tarjeta Visa").
     */
    private String name;

    /**
     * Tipo de cuenta financiera.
     * @see AccountType
     */
    @Enumerated(EnumType.STRING)
    private AccountType type;
    
    /**
     * Balance o saldo actual de la cuenta en la moneda del sistema.
     */
    private Double balance;

    /**
     * Usuario propietario de la cuenta.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Lista de transacciones realizadas en esta cuenta.
     * Relación uno a muchos con la entidad Transaction.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    /**
     * Lista de automatizaciones configuradas para esta cuenta.
     * Relación uno a muchos con la entidad Automation.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    private List<Automation> automations;
}
