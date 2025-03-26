package uta.ec.finance_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private Integer id;
    private String name;
    private String type;
    private Double balance;
    private Integer userId;
}
