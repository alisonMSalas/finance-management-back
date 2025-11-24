package uta.ec.finance_manager.service;

import io.jsonwebtoken.Jwt;
import uta.ec.finance_manager.config.auth.AuthenticationRequest;
import uta.ec.finance_manager.dto.SaveUserDto;
import uta.ec.finance_manager.dto.UserDto;

/**
 * Servicio para la autenticación y registro de usuarios.
 * <p>
 * Esta interfaz define las operaciones disponibles para gestionar
 * el registro de nuevos usuarios y el inicio de sesión,
 * generando tokens JWT para la autenticación de solicitudes.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface AuthService {
    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * Crea una cuenta de usuario con la información proporcionada,
     * encriptando la contraseña antes de almacenarla en la base de datos.
     * </p>
     * 
     * @param request datos del usuario a registrar (nombre, email, contraseña)
     * @return usuario registrado sin información sensible
     */
    UserDto register(SaveUserDto request);

    /**
     * Autentica a un usuario y genera un token JWT.
     * <p>
     * Valida las credenciales del usuario y, si son correctas,
     * genera un token JWT que puede ser utilizado para autenticar
     * solicitudes futuras a los endpoints protegidos.
     * </p>
     * 
     * @param authenticationRequest credenciales de autenticación (email y contraseña)
     * @return token JWT para autenticación
     */
    String login(AuthenticationRequest authenticationRequest);
}
