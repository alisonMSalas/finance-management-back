package uta.ec.finance_manager.dto;

import jakarta.persistence.Access;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class InvestmentDto {

    private Integer id;

    @NotNull(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El monto inicial es obligatorio")
    private Double initialAmount;

    @NotNull(message = "El porcemtaje de retorno es obligatorio")
    private Double returnRate;

    @NotNull(message = "La fecha de inicio es obligatorio")
    private Date startDate;

    @NotNull(message = "La fecha de finalizacion es obligatorio")
    private Date endDate;

    private Integer progress;
    private Integer userId;

}
