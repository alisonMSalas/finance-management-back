package uta.ec.finance_manager.dto;

import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.CategoryType;

import java.util.Date;

@Getter
@Setter
public class AutomationDto {
    private Integer id;
    private Double amount;
    private String frequency;
    private Date startDate;
    private CategoryType categoryType;
    private Integer userId;
    private Integer accountId;
}
