package uta.ec.finance_manager.util;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Utilidad para el manejo y cálculo de fechas.
 * <p>
 * Esta clase proporciona métodos para obtener fechas de inicio y fin
 * de períodos específicos (semana, mes, año), facilitando el filtrado
 * y análisis de transacciones y presupuestos por períodos de tiempo.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Service
public class DateUtil {
    /**
     * Obtiene la fecha de inicio de la semana actual (lunes a las 00:00:00).
     * 
     * @return fecha de inicio de la semana
     */
    public Date getStartOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        return toDate(startOfWeek.atStartOfDay());
    }

    /**
     * Obtiene la fecha de fin de la semana actual (domingo a las 23:59:59).
     * 
     * @return fecha de fin de la semana
     */
    public Date getEndOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        return toDate(endOfWeek.atTime(LocalTime.MAX));
    }

    /**
     * Obtiene la fecha de inicio del mes actual (día 1 a las 00:00:00).
     * 
     * @return fecha de inicio del mes
     */
    public Date getStartOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        return toDate(startOfMonth.atStartOfDay());
    }

    /**
     * Obtiene la fecha de fin del mes actual (último día a las 23:59:59).
     * 
     * @return fecha de fin del mes
     */
    public Date getEndOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return toDate(endOfMonth.atTime(LocalTime.MAX));
    }

    /**
     * Obtiene la fecha de inicio del año actual (1 de enero a las 00:00:00).
     * 
     * @return fecha de inicio del año
     */
    public Date getStartOfYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        return toDate(startOfYear.atStartOfDay());
    }

    /**
     * Obtiene la fecha de fin del año actual (31 de diciembre a las 23:59:59).
     * 
     * @return fecha de fin del año
     */
    public Date getEndOfYear() {
        LocalDate today = LocalDate.now();
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return toDate(endOfYear.atTime(LocalTime.MAX));
    }

    /**
     * Convierte un LocalDateTime a Date.
     * 
     * @param dateTime objeto LocalDateTime a convertir
     * @return objeto Date equivalente
     */
    private Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
