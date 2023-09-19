package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class CommunityDTO {
    @Positive
    @Schema(description = "Qualquer ID genérico", example = "1")
    private Integer idCommunity;

    @Schema(description = "Nome da comunidade", example = "DevsJonadas")
    @NotBlank
    @NotNull
    @Size(min = 3, max = 30, message = "Comunidade deve conter entre 3 e 30 caracteres")
    private String nameCommunity;

    @Schema(description = "Descrição da comunidade", example = "comunidade para devs do jornada tech")
    @NotBlank
    @NotNull
    private String descriptionCommunity;

//    @Schema(description = "Membros da comunidade")
//    private String membersCommunity;

//    @Field(name = "Data de criação")
//    @FutureOrPresent
//    private Date creationDate;
}
