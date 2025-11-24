package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.AccountDto;

import java.util.List;

/**
 * Servicio para la gestión de cuentas financieras.
 * <p>
 * Esta interfaz define las operaciones disponibles para administrar
 * las cuentas de los usuarios en el sistema de gestión financiera.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface AccountService {
    /**
     * Guarda una nueva cuenta en el sistema.
     * 
     * @param accountDto datos de la cuenta a crear
     * @return cuenta creada con su identificador asignado
     */
    AccountDto save(AccountDto accountDto);

    /**
     * Obtiene todas las cuentas del usuario autenticado.
     * 
     * @return lista de cuentas del usuario actual
     */
    List<AccountDto> getAllByUser();

    /**
     * Edita una cuenta existente.
     * 
     * @param accountDto datos actualizados de la cuenta
     * @return cuenta actualizada
     */
    AccountDto edit(AccountDto accountDto);

    /**
     * Elimina una cuenta del sistema.
     * 
     * @param accountId identificador de la cuenta a eliminar
     */
    void delete(Integer accountId);

    /**
     * Calcula el balance total de todas las cuentas del usuario autenticado.
     * 
     * @return suma total de los balances de todas las cuentas
     */
    Double getTotalBalance();

    /**
     * Busca cuentas por nombre para el usuario autenticado.
     * 
     * @param name nombre o parte del nombre de la cuenta a buscar
     * @return lista de cuentas que coinciden con el criterio de búsqueda
     */
    List<AccountDto> getAllByName(String name);
}
