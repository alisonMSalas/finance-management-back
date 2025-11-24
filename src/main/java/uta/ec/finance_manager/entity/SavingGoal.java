package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entidad que representa una meta de ahorro en el sistema.
 * <p>
 * Una meta de ahorro permite al usuario establecer objetivos financieros
 * específicos con un monto objetivo, seguimiento del progreso actual
 * y una fecha límite para alcanzar la meta.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "savings_goal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoal {
    /**
     * Identificador único de la meta de ahorro.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Nombre descriptivo de la meta de ahorro (ej: "Viaje a Europa", "Fondo de Emergencia").
     */
    private String name;
    
    /**
     * Monto objetivo que se desea alcanzar.
     */
    private Double targetAmount;
    
    /**
     * Balance actual ahorrado hacia la meta.
     * Se actualiza a medida que el usuario realiza ahorros.
     */
    private Double currentBalance;
    
    /**
     * Fecha límite para alcanzar la meta de ahorro.
     */
    private Date deadline;

    /**
     * Usuario propietario de la meta de ahorro.
     * Relación muchos a uno con la entidad User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
