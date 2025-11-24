package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.service.AccountService;

import java.util.List;

/**
 * Controlador REST para la gestión de cuentas financieras.
 * <p>
 * Este controlador expone los endpoints RESTful para realizar operaciones CRUD
 * sobre las cuentas de los usuarios, incluyendo consultas de balance total
 * y búsqueda por nombre.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * Crea una nueva cuenta financiera para el usuario autenticado.
     * <p>
     * <b>Endpoint:</b> POST /account<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Ejemplo de request body:</b></p>
     * <pre>
     * {
     *   "name": "Cuenta de Ahorros",
     *   "type": "BANK_ACCOUNT",
     *   "balance": 1000.00
     * }
     * </pre>
     * 
     * @param accountDto datos de la cuenta a crear (nombre, tipo y balance son obligatorios)
     * @return cuenta creada con su identificador asignado
     */
    @PostMapping
    public AccountDto createAccount(@Valid @RequestBody AccountDto accountDto){
        return this.accountService.save(accountDto);
    }

    /**
     * Obtiene todas las cuentas del usuario autenticado.
     * <p>
     * <b>Endpoint:</b> GET /account<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Respuesta:</b> Array JSON de cuentas
     * </p>
     * 
     * @return lista de cuentas del usuario actual
     */
    @GetMapping
    public List<AccountDto> getUserAccounts(){
        return accountService.getAllByUser();
    }

    /**
     * Actualiza los datos de una cuenta existente.
     * <p>
     * <b>Endpoint:</b> PUT /account<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Nota:</b> El ID de la cuenta debe estar incluido en el request body.</p>
     * 
     * @param accountDto datos actualizados de la cuenta (debe incluir el ID)
     * @return cuenta actualizada
     */
    @PutMapping
    public AccountDto editAccount(@Valid @RequestBody AccountDto accountDto){
        return accountService.edit(accountDto);
    }

    /**
     * Elimina una cuenta del sistema.
     * <p>
     * <b>Endpoint:</b> DELETE /account?accountId={id}<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Parámetro query:</b> accountId (Integer)
     * </p>
     * 
     * <p><b>Ejemplo:</b> DELETE /account?accountId=5</p>
     * 
     * @param accountId identificador de la cuenta a eliminar
     */
    @DeleteMapping
    public void deleteAccount(@RequestParam Integer accountId){
        accountService.delete(accountId);
    }

    /**
     * Calcula y retorna el balance total de todas las cuentas del usuario.
     * <p>
     * <b>Endpoint:</b> GET /account/total-balance<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Respuesta:</b> Número decimal (Double)
     * </p>
     * 
     * <p><b>Ejemplo de respuesta:</b> 5250.75</p>
     * 
     * @return suma total de los balances de todas las cuentas
     */
    @GetMapping("/total-balance")
    public Double getTotalBalance() {
        return accountService.getTotalBalance();
    }

    /**
     * Busca cuentas por nombre para el usuario autenticado.
     * <p>
     * <b>Endpoint:</b> GET /account/name/{name}<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Parámetro path:</b> name (String)
     * </p>
     * 
     * <p><b>Ejemplo:</b> GET /account/name/ahorros</p>
     * <p>Búsqueda parcial: retorna todas las cuentas que contengan el texto especificado.</p>
     * 
     * @param name nombre o parte del nombre a buscar
     * @return lista de cuentas que coinciden con el criterio
     */
    @GetMapping("/name/{name}")
    public List<AccountDto> getAllByName(@PathVariable String name) {
        return accountService.getAllByName(name);
    }
}
