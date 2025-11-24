package uta.ec.finance_manager.enums;

/**
 * Enumeración de los tipos de cuenta financiera disponibles en el sistema.
 * <p>
 * Define los diferentes tipos de cuentas que un usuario puede crear
 * para administrar sus finanzas personales.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public enum AccountType {
    /** Tarjeta de crédito */
    CREDIT_CARD,
    
    /** Tarjeta de débito */
    DEBIT_CARD,
    
    /** Cuenta bancaria */
    BANK_ACCOUNT,
    
    /** Efectivo */
    CASH
}
