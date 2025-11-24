package uta.ec.finance_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.service.AccountService;

import java.util.List;

/**
 * Controlador REST para la gestión de cuentas bancarias.
 * Proporciona endpoints para crear, consultar, actualizar y eliminar cuentas.
 * 
 * @author Finance Management Team
 * @version 1.0
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Cuentas", description = "API para la gestión de cuentas bancarias")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Crear cuenta", description = "Crea una nueva cuenta bancaria para el usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public AccountDto createAccount(@Valid @RequestBody AccountDto accountDto){
        return this.accountService.save(accountDto);
    }

    @GetMapping
    @Operation(summary = "Listar cuentas del usuario", description = "Obtiene todas las cuentas del usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public List<AccountDto> getUserAccounts(){
        return accountService.getAllByUser();
    }

    @PutMapping
    @Operation(summary = "Actualizar cuenta", description = "Actualiza la información de una cuenta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public AccountDto editAccount(@Valid @RequestBody AccountDto accountDto){
        return accountService.edit(accountDto);
    }

    @DeleteMapping
    @Operation(summary = "Eliminar cuenta", description = "Elimina una cuenta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta eliminada exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public void deleteAccount(
            @Parameter(description = "ID de la cuenta a eliminar", required = true)
            @RequestParam Integer accountId){
        accountService.delete(accountId);
    }

    @GetMapping("/total-balance")
    @Operation(summary = "Obtener balance total", description = "Calcula el balance total de todas las cuentas del usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Balance total calculado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public Double getTotalBalance() {
        return accountService.getTotalBalance();
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar cuentas por nombre", description = "Busca cuentas que coincidan con el nombre especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public List<AccountDto> getAllByName(
            @Parameter(description = "Nombre de la cuenta a buscar", required = true)
            @PathVariable String name) {
        return accountService.getAllByName(name);
    }
}
