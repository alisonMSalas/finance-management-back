package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.AccountType;

@Getter
@Setter
public class AccountDto {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType type;

    @NotNull(message = "El balance es obligatorio")
    private Double balance;

    private Integer userId;
}