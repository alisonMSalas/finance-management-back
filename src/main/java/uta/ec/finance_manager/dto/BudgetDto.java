package uta.ec.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.CategoryType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Integer id;
    private Double maxAmount;
    private Date startDate;
    private Date endDate;
    private CategoryType category;
    private Integer userId;
}
