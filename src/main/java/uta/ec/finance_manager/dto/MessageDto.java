package uta.ec.finance_manager.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String type; // INFO, ERROR, WARNING
    private String content;
    private Date timestamp;
}
