package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @file User.java
 * @brief Entidad que representa un usuario del sistema
 *
 * Esta clase define la estructura de un usuario en el sistema de gestión
 * financiera, incluyendo sus relaciones con transacciones, cuentas y mensajes.
 *
 * @author Finance Manager Team
 * @version 1.0
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    /**
     * @brief Identificador único del usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @brief Nombre de usuario único en el sistema
     */
    @Column(unique = true)
    private String name;

    /**
     * @brief Correo electrónico del usuario
     */
    private String email;

    /**
     * @brief Contraseña encriptada del usuario
     */
    private String password;

    /**
     * @brief Lista de transacciones asociadas al usuario
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    /**
     * @brief Lista de cuentas bancarias del usuario
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;

    /**
     * @brief Lista de mensajes/notificaciones del usuario
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages;
}
