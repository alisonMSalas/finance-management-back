package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.BudgetDto;

import java.util.List;

/**
 * Servicio para la gestión de presupuestos.
 * <p>
 * Esta interfaz define las operaciones disponibles para administrar
 * los presupuestos de los usuarios, permitiendo establecer y controlar
 * límites de gasto por categoría y período.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface BudgetService {

    /**
     * Crea un nuevo presupuesto.
     * 
     * @param budgetDto datos del presupuesto a crear
     * @return presupuesto creado con su identificador asignado
     */
    BudgetDto save(BudgetDto budgetDto);

    /**
     * Obtiene todos los presupuestos del usuario autenticado.
     * 
     * @return lista de presupuestos del usuario actual
     */
    List<BudgetDto> getAll();

    /**
     * Actualiza un presupuesto existente.
     * 
     * @param budgetDto datos actualizados del presupuesto
     * @return presupuesto actualizado
     */
    BudgetDto update(BudgetDto budgetDto);

    /**
     * Elimina un presupuesto del sistema.
     * 
     * @param budgetId identificador del presupuesto a eliminar
     */
    void delete(Integer budgetId);
}
