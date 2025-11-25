package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.service.AccountService;

import java.util.List;

/**
 * @brief Controlador REST para la gestión de cuentas financieras
 * @details Proporciona endpoints para operaciones CRUD sobre cuentas de usuario
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * @brief Endpoint para crear una cuenta nueva
     * @param accountDto Datos de la cuenta a crear
     * @return AccountDto La cuenta creada con su ID asignado
     */
    @PostMapping
    public AccountDto createAccount(@Valid @RequestBody AccountDto accountDto){
        return this.accountService.save(accountDto);
    }

    /**
     * @brief Obtiene todas las cuentas del usuario autenticado
     * @return Lista de cuentas del usuario actual
     */
    @GetMapping
    public List<AccountDto> getUserAccounts(){
        return accountService.getAllByUser();
    }

    /**
     * @brief Edita una cuenta existente
     * @param accountDto Datos actualizados de la cuenta
     * @return AccountDto La cuenta editada
     */
    @PutMapping
    public AccountDto editAccount(@Valid @RequestBody AccountDto accountDto){
        return accountService.edit(accountDto);
    }

    /**
     * @brief Elimina una cuenta por su ID
     * @param accountId ID de la cuenta a eliminar
     */
    @DeleteMapping
    public void deleteAccount(@RequestParam Integer accountId){
        accountService.delete(accountId);
    }

    /**
     * @brief Obtiene el balance total de todas las cuentas del usuario
     * @return Balance total sumado de todas las cuentas
     */
    @GetMapping("/total-balance")
    public Double getTotalBalance() {
        return accountService.getTotalBalance();
    }

    /**
     * @brief Busca cuentas por nombre
     * @param name Nombre o parte del nombre a buscar
     * @return Lista de cuentas que coinciden con el nombre
     */
    @GetMapping("/name/{name}")
    public List<AccountDto> getAllByName(@PathVariable String name) {
        return accountService.getAllByName(name);
    }
}
