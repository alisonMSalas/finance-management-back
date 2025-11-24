package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.AccountType;

/**
 * Objeto de transferencia de datos para la entidad Account.
 * <p>
 * Este DTO se utiliza para transportar información de cuentas financieras
 * entre las capas de la aplicación, incluyendo validaciones de entrada.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Getter
@Setter
public class AccountDto {

    /**
     * Identificador único de la cuenta.
     */
    private Integer id;

    /**
     * Nombre descriptivo de la cuenta.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    /**
     * Tipo de cuenta financiera (bancaria, efectivo, etc.).
     * Es un campo obligatorio.
     * @see AccountType
     */
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType type;

    /**
     * Balance o saldo actual de la cuenta.
     * Es un campo obligatorio.
     */
    @NotNull(message = "El balance es obligatorio")
    private Double balance;

    /**
     * Identificador del usuario propietario de la cuenta.
     */
    private Integer userId;
}