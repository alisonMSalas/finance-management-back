package uta.ec.finance_manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uta.ec.finance_manager.enums.TransactionCategory;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Guayaquil")
    private Date date;

    @NotNull(message = "La cuenta es obligatoria")
    private Integer accountId;

    @NotNull(message = "La categoria es obligatoria")
    private TransactionCategory category;

    private Integer userId;
    private String description;
}
