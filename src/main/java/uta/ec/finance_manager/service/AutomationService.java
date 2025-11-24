package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.entity.Automation;

import java.util.List;

/**
 * Servicio para la gestión de automatizaciones de transacciones.
 * <p>
 * Esta interfaz define las operaciones disponibles para administrar
 * las transacciones automáticas recurrentes de los usuarios,
 * permitiendo programar ingresos y gastos que se repiten periódicamente.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface AutomationService {
    /**
     * Crea una nueva automatización de transacciones.
     * 
     * @param automationDto datos de la automatización a crear
     * @return automatización creada con su identificador asignado
     */
    AutomationDto create(AutomationDto automationDto);

    /**
     * Obtiene todas las automatizaciones de un usuario específico.
     * 
     * @param userId identificador del usuario
     * @return lista de automatizaciones del usuario especificado
     */
    List<AutomationDto> getAutomationsByUser(Integer userId);

    /**
     * Edita una automatización existente.
     * 
     * @param automationDto datos actualizados de la automatización
     * @return automatización actualizada
     */
    AutomationDto edit(AutomationDto automationDto);

    /**
     * Elimina una automatización del sistema.
     * 
     * @param automationId identificador de la automatización a eliminar
     */
    void delete(Integer automationId);
}
