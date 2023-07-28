package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {
    @Positive
    @Schema(description = "Qualquer ID genérico", example = "1")
    private Integer codigoUser;
    @Schema(description = "Colocar e-mail do usuário", example = "name@gmail.com")
    @NotBlank
    @NotNull
    @Email
    private String email;
    @Schema(description = "Colocar senha do usuário", example = "Senha-Segura")
    @NotBlank
    @NotNull
    private String senha;
    @Schema(description = "Nome Completo", example = "Fulano de Tal")
    @NotBlank
    @NotNull
    @Size(min = 3, max = 30, message = "Usuário deve conter entre 3 e 30 caracteres")
    private String nome;

}

