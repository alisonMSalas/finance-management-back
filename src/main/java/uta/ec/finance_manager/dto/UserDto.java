package uta.ec.finance_manager.dto;

import lombok.Data;

/**
 * Objeto de transferencia de datos para la entidad User.
 * <p>
 * Este DTO se utiliza para transportar información de usuarios
 * entre las capas de la aplicación. Contiene solo los datos
 * básicos del usuario sin información sensible como contraseñas.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Data
public class UserDto {
    /**
     * Identificador único del usuario.
     */
    Integer id;
    
    /**
     * Nombre de usuario.
     */
    String name;
    
    /**
     * Correo electrónico del usuario.
     */
    String email;
}
