package uta.ec.finance_manager.enums;

/**
 * Enumeración de las categorías de transacciones disponibles en el sistema.
 * <p>
 * Define las diferentes categorías para clasificar los ingresos y gastos
 * del usuario, facilitando el análisis y control de sus finanzas personales.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public enum TransactionCategory {
    /** Gastos relacionados con alimentación */
    COMIDA,
    
    /** Gastos de transporte y movilización */
    TRANSPORTE,
    
    /** Ingresos por sueldo o salario */
    SUELDO,
    
    /** Gastos relacionados con salud y medicina */
    SALUD,
    
    /** Gastos de vivienda (alquiler, servicios, etc.) */
    VIVIENDA,
    
    /** Gastos de entretenimiento y ocio */
    ENTRETENIMIENTO,
    
    /** Otras categorías no especificadas */
    OTROS
}
