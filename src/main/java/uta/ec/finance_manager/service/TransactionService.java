package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.TransactionDto;

import java.util.List;

/**
 * Servicio para la gestión de transacciones financieras.
 * <p>
 * Esta interfaz define las operaciones disponibles para administrar
 * las transacciones de ingresos y gastos de los usuarios,
 * incluyendo su creación, edición y consulta.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface TransactionService {
    /**
     * Registra una nueva transacción en el sistema.
     * 
     * @param dto datos de la transacción a crear
     * @return transacción creada con su identificador asignado
     */
    TransactionDto save(TransactionDto dto);
    
    /**
     * Edita una transacción existente.
     * 
     * @param transactionId identificador de la transacción a editar
     * @param dto datos actualizados de la transacción
     * @return transacción actualizada
     */
    TransactionDto edit(Integer transactionId, TransactionDto dto);
    
    /**
     * Obtiene todas las transacciones del usuario autenticado.
     * 
     * @return lista de transacciones del usuario actual
     */
    List<TransactionDto> getAllByUser();
}
