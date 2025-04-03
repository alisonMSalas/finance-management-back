package uta.ec.finance_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveUserDto {
    @NotBlank(message = "El nombre es obligatorio")
    String name;

    @Email(message = "El correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    String email;

    @NotBlank(message = "La contraseña es obligatoria")
    String password;
}
