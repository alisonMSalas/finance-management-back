package uta.ec.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uta.ec.finance_manager.enums.Category;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Integer id;
    private Double maxAmount;
    private Date startDate;
    private Date endDate;
    private Category category;
    private Integer userId;
}
