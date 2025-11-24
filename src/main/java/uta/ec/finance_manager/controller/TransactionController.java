package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.dto.TransactionDto;
import uta.ec.finance_manager.service.AccountService;
import uta.ec.finance_manager.service.TransactionService;

import java.util.List;

/**
 * Controlador REST para la gestión de transacciones financieras.
 * <p>
 * Este controlador expone los endpoints RESTful para realizar operaciones CRUD
 * sobre las transacciones de los usuarios, permitiendo registrar ingresos y gastos,
 * editarlos y consultar el historial completo.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Registra una nueva transacción en el sistema.
     * <p>
     * <b>Endpoint:</b> POST /transaction<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Ejemplo de request body:</b></p>
     * <pre>
     * {
     *   "amount": 150.50,
     *   "type": "EGRESO",
     *   "date": "2024-11-24",
     *   "accountId": 1,
     *   "category": "COMIDA",
     *   "description": "Supermercado"
     * }
     * </pre>
     * 
     * @param transactionDto datos de la transacción a crear
     * @return transacción creada con su identificador asignado
     */
    @PostMapping
    public TransactionDto save(@Valid @RequestBody TransactionDto transactionDto) {
        return transactionService.save(transactionDto);
    }

    /**
     * Edita una transacción existente.
     * <p>
     * <b>Endpoint:</b> PUT /transaction?transactionId={id}<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Parámetro query:</b> transactionId (Integer)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Ejemplo:</b> PUT /transaction?transactionId=10</p>
     * 
     * @param transactionId identificador de la transacción a editar
     * @param transactionDto datos actualizados de la transacción
     * @return transacción actualizada
     */
    @PutMapping
    public TransactionDto edit(@RequestParam Integer transactionId, @Valid @RequestBody TransactionDto transactionDto) {
        return transactionService.edit(transactionId, transactionDto);
    }

    /**
     * Obtiene todas las transacciones del usuario autenticado.
     * <p>
     * <b>Endpoint:</b> GET /transaction<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Respuesta:</b> Array JSON de transacciones ordenadas por fecha
     * </p>
     * 
     * @return lista de transacciones del usuario actual
     */
    @GetMapping
    public List<TransactionDto> getAllByUSer() {
        return  transactionService.getAllByUser();
    }
}
