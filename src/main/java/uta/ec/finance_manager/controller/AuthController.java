package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.config.auth.AuthenticationRequest;
import uta.ec.finance_manager.dto.SaveUserDto;
import uta.ec.finance_manager.dto.UserDto;
import uta.ec.finance_manager.service.AuthService;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * <p>
 * Este controlador expone los endpoints públicos para el registro de nuevos
 * usuarios y el inicio de sesión, generando tokens JWT para autenticación.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * <b>Endpoint:</b> POST /api/register<br>
     * <b>Requiere autenticación:</b> No (Endpoint público)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Ejemplo de request body:</b></p>
     * <pre>
     * {
     *   "name": "Juan Pérez",
     *   "email": "juan@example.com",
     *   "password": "miPassword123"
     * }
     * </pre>
     * 
     * <p>
     * Crea una cuenta de usuario con la información proporcionada,
     * encriptando la contraseña antes de almacenarla.
     * </p>
     * 
     * @param request datos del usuario a registrar (nombre, email, contraseña)
     * @return usuario registrado sin información sensible
     */
    @PostMapping("/register")
    public UserDto registerUser(@Valid @RequestBody SaveUserDto request) {
        return authService.register(request);
    }

    /**
     * Inicia sesión de un usuario en el sistema.
     * <p>
     * <b>Endpoint:</b> POST /api/login<br>
     * <b>Requiere autenticación:</b> No (Endpoint público)<br>
     * <b>Tipo de contenido:</b> application/json<br>
     * <b>Respuesta:</b> Token JWT (String)
     * </p>
     * 
     * <p><b>Ejemplo de request body:</b></p>
     * <pre>
     * {
     *   "email": "juan@example.com",
     *   "password": "miPassword123"
     * }
     * </pre>
     * 
     * <p><b>Ejemplo de respuesta:</b></p>
     * <pre>"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."</pre>
     * 
     * <p>
     * Autentica al usuario con sus credenciales y genera un token JWT
     * para acceder a los endpoints protegidos. El token debe incluirse
     * en el header Authorization de las siguientes peticiones:
     * <code>Authorization: Bearer {token}</code>
     * </p>
     * 
     * @param authenticationRequest credenciales de autenticación (email y contraseña)
     * @return token JWT para autenticación de solicitudes futuras
     */
    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }
}