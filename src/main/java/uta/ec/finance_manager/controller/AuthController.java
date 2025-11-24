package uta.ec.finance_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.config.auth.AuthenticationRequest;
import uta.ec.finance_manager.dto.SaveUserDto;
import uta.ec.finance_manager.dto.UserDto;
import uta.ec.finance_manager.service.AuthService;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * 
 * @author Finance Management Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "API para registro y login de usuarios")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "El usuario ya existe")
    })
    public UserDto registerUser(@Valid @RequestBody SaveUserDto request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public String login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }
}