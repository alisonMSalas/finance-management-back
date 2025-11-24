package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.service.AutomationService;

import java.util.List;

/**
 * Controlador REST para la gestión de transacciones automáticas.
 * <p>
 * Este controlador expone los endpoints RESTful para realizar operaciones CRUD
 * sobre las automatizaciones de transacciones, permitiendo a los usuarios
 * programar transacciones recurrentes que se ejecutarán automáticamente.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/automation")
@RequiredArgsConstructor
public class AutomationController {
    private final AutomationService automationService;
    
    /**
     * Crea una nueva automatización de transacciones.
     * <p>
     * <b>Endpoint:</b> POST /automation<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Ejemplo de request body:</b></p>
     * <pre>
     * {
     *   "amount": 300.00,
     *   "description": "Renta mensual",
     *   "frequency": "MENSUAL",
     *   "accountId": 1,
     *   "startDate": "2024-01-01"
     * }
     * </pre>
     * 
     * @param automationDto datos de la automatización a crear
     * @return automatización creada con su identificador asignado
     */
    @PostMapping()
    public AutomationDto createAutomation(@Valid @RequestBody AutomationDto automationDto){
        return this.automationService.create(automationDto);
    }

    /**
     * Obtiene todas las automatizaciones de un usuario específico.
     * <p>
     * <b>Endpoint:</b> GET /automation?userId={id}<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Parámetro query:</b> userId (Integer)<br>
     * <b>Respuesta:</b> Array JSON de automatizaciones
     * </p>
     * 
     * <p><b>Ejemplo:</b> GET /automation?userId=5</p>
     * 
     * @param userId identificador del usuario
     * @return lista de automatizaciones del usuario especificado
     */
    @GetMapping()
    public List<AutomationDto> getAutomationByUser(@RequestParam Integer userId){
        return this.automationService.getAutomationsByUser(userId);
    }

    /**
     * Modifica una automatización existente.
     * <p>
     * <b>Endpoint:</b> PUT /automation<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Tipo de contenido:</b> application/json
     * </p>
     * 
     * <p><b>Nota:</b> El ID de la automatización debe estar incluido en el request body.</p>
     * 
     * @param automationDto datos actualizados de la automatización (debe incluir el ID)
     * @return automatización actualizada
     */
    @PutMapping()
    public AutomationDto modifyAutomation(@Valid @RequestBody AutomationDto automationDto){
        return this.automationService.edit(automationDto);
    }

    /**
     * Elimina una automatización del sistema.
     * <p>
     * <b>Endpoint:</b> DELETE /automation?automationId={id}<br>
     * <b>Requiere autenticación:</b> Sí (Token JWT)<br>
     * <b>Parámetro query:</b> automationId (Integer)
     * </p>
     * 
     * <p><b>Ejemplo:</b> DELETE /automation?automationId=7</p>
     * 
     * @param automationId identificador de la automatización a eliminar
     */
    @DeleteMapping()
    public void deleteAutomation(@RequestParam Integer automationId){
        this.automationService.delete(automationId);
    }
}
