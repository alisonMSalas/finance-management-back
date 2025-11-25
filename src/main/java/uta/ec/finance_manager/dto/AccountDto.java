package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.AccountType;

/**
 * @brief DTO para transferencia de datos de cuentas financieras
 * @details Representa la información de una cuenta incluyendo validaciones
 */
@Getter
@Setter
public class AccountDto {

    /**
     * @brief Identificador único de la cuenta
     */
    private Integer id;

    /**
     * @brief Nombre descriptivo de la cuenta
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    /**
     * @brief Tipo de cuenta (SAVINGS, CHECKING, INVESTMENT, etc.)
     */
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType type;

    /**
     * @brief Balance actual de la cuenta
     */
    @NotNull(message = "El balance es obligatorio")
    private Double balance;

    /**
     * @brief ID del usuario propietario de la cuenta
     */
    private Integer userId;
}