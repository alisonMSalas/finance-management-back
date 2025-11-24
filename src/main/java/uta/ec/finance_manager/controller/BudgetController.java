package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.BudgetDto;
import uta.ec.finance_manager.service.BudgetService;

import java.util.List;

/**
 * Controlador REST para la gestión de presupuestos.
 * <p>
 * Este controlador expone los endpoints RESTful para realizar operaciones CRUD
 * sobre los presupuestos de los usuarios, permitiendo establecer límites de gasto
 * por categoría y período de tiempo.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("budget")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    /**
     * Crea un nuevo presupuesto para el usuario autenticado.
     * <p>
     * <b>Endpoint:</b> POST /budget<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Ejemplo de request body:</b></p>
     * <pre>
     * {
     *   "amount": 500.00,
     *   "category": "COMIDA",
     *   "period": "MENSUAL"
     * }
     * </pre>
     * 
     * @param budgetDto datos del presupuesto a crear (monto, categoría y período son obligatorios)
     * @return presupuesto creado con su identificador asignado
     */
    @PostMapping
    public BudgetDto save(@Valid @RequestBody BudgetDto budgetDto) {
        return budgetService.save(budgetDto);
    }

    /**
     * Obtiene todos los presupuestos del usuario autenticado.
     * <p>
     * <b>Endpoint:</b> GET /budget<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Respuesta:</b> Array JSON de presupuestos
     * </p>
     * 
     * @return lista de presupuestos del usuario actual
     */
    @GetMapping
    public List<BudgetDto> getAll() {
        return budgetService.getAll();
    }

    /**
     * Actualiza un presupuesto existente.
     * <p>
     * <b>Endpoint:</b> PUT /budget<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Nota:</b> El ID del presupuesto debe estar incluido en el request body.</p>
     * 
     * @param budgetDto datos actualizados del presupuesto (debe incluir el ID)
     * @return presupuesto actualizado
     */
    @PutMapping
    public BudgetDto update(@Valid @RequestBody BudgetDto budgetDto) {
        return budgetService.update(budgetDto);
    }

    /**
     * Elimina un presupuesto del sistema.
     * <p>
     * <b>Endpoint:</b> DELETE /budget?budgetId={id}<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Parámetro query:</b> budgetId (Integer)
     * </p>
     * 
     * <p><b>Ejemplo:</b> DELETE /budget?budgetId=3</p>
     * 
     * @param budgetId identificador del presupuesto a eliminar
     */
    @DeleteMapping
    public void delete(@RequestParam Integer budgetId) {
        budgetService.delete(budgetId);
    }
}
