package uta.ec.finance_manager.dto;

import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.AccountType;

@Getter
@Setter
public class AccountDto {
    private Integer id;
    private String name;
    private AccountType type;
    private Double balance;
    private Integer userId;
}
