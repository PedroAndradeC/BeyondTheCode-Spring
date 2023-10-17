package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class CommunityDTO {

    @Schema(description = "Qualquer ID genérico", example = "asdf21af5a5s45")
    private String idCommunity;

    @Schema(description = "Nome da comunidade", example = "DevsJonadas")
    @NotBlank
    @NotNull
    @Size(min = 3, max = 30, message = "Comunidade deve conter entre 3 e 30 caracteres")
    private String nameCommunity;

    @Schema(description = "Assunto da comunidade", example = "Tecnologia")
    @NotBlank
    @NotNull
    @Size(min = 3, max = 30, message = "Assunto da comunidade deve conter entre 3 e 30 caracteres")
    private String communityTopic;

    private DescriptionCommunity descriptionCommunity;
}
