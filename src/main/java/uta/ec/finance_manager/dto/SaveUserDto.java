package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Objeto de transferencia de datos para el registro de nuevos usuarios.
 * <p>
 * Este DTO se utiliza específicamente para el proceso de registro,
 * incluyendo la contraseña del usuario. Contiene validaciones estrictas
 * para asegurar que todos los campos requeridos estén presentes y sean válidos.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 * @see uta.ec.finance_manager.dto.UserDto
 */
@Data
public class SaveUserDto {
    /**
     * Nombre de usuario.
     * Campo obligatorio, no puede estar vacío.
     */
    @NotBlank(message = "El nombre es obligatorio")
    String name;

    /**
     * Correo electrónico del usuario.
     * Debe ser un email válido y es obligatorio.
     */
    @Email(message = "El correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    String email;

    /**
     * Contraseña del usuario.
     * Campo obligatorio, se encriptará antes de almacenarse.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    String password;
}
