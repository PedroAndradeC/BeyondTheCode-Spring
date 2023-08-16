package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRetornoDTO {

    @Positive
    @Schema(description = "Qualquer ID genérico", example = "1")
    private Integer id;
    @Schema(description = "Nome Completo", example = "Fulano de Tal")
    @NotBlank
    @NotNull
    @Size(min = 3, max = 30, message = "Usuário deve conter entre 3 e 30 caracteres")
    private String name;
    @Schema(description = "Colocar e-mail do usuário", example = "name@gmail.com")
    @NotBlank
    @NotNull
    @Email
    private String email;
}
