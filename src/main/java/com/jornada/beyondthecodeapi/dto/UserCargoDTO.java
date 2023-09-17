package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserCargoDTO {
    @Positive
    @Schema(description = "Qualquer ID genérico", example = "1")
    private Integer idUser;
    @Positive
    @Schema(description = "Qualquer ID genérico", example = "1")
    private Integer idCargo;
}
