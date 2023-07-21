package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserDTO {
    @Positive
    @Schema(description = "Qualquer ID genérico", example = "1")
    private Integer id;
    @Schema(description = "Colocar e-mail do usuário", example = "Jefte@gmail.com")
    @NotBlank
    @NotNull
    private String email;
    @Schema(description = "Colocar senha do usuário", example = "Senha-Segura")
    @NotBlank
    @NotNull
    private String password;
    @Schema(description = "Nome Completo", example = "Jefte Gonçalves")
    @NotBlank
    @NotNull
    private String name;

}
