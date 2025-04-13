package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionDto {
    private Integer id;
    @NotNull(message = "La cantidad es obligatoria")
    private Double amount;
    @NotNull(message = "El tipo es obligatorio")
    private String type;
    @NotNull(message = "La fecha es obligatoria")
    private Date date;
    @NotNull(message = "La cuenta es obligatoria")
    private Integer accountId;
    private Integer userId;
    private String description;
}
