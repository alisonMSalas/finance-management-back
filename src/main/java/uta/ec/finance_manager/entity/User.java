package uta.ec.finance_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad que representa a un usuario del sistema de gestión financiera.
 * <p>
 * Esta clase almacena la información básica del usuario incluyendo sus credenciales
 * y relaciones con otras entidades como transacciones y cuentas.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
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
     * Identificador único del usuario.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de usuario único en el sistema.
     * Debe ser único para cada usuario.
     */
    @Column(unique = true)
    private String name;
    
    /**
     * Correo electrónico del usuario.
     */
    private String email;
    
    /**
     * Contraseña del usuario almacenada de forma encriptada.
     */
    private String password;

    /**
     * Lista de transacciones asociadas al usuario.
     * Relación uno a muchos con la entidad Transaction.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    /**
     * Lista de cuentas financieras asociadas al usuario.
     * Relación uno a muchos con la entidad Account.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;
}
