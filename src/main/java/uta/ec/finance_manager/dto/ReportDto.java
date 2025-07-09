package uta.ec.finance_manager.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private Double incomes;
    private Double expenses;
    private Double budget;
    private Double accounts;
}
