package com.jornada.beyondthecodeapi.dto;

import com.jornada.beyondthecodeapi.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PostDTO {
    @Schema(description = "Colocar Conteudo do Post", example = "Lorem Ipsum Sit Dolor Amet")
    @NotBlank
    @NotNull
    private String contents;
    @Schema(description = "Colocar titulo do Post", example = "Lorem Ipsum Sit Dolor Amet")
    @NotBlank
    @NotNull
    private String title;
    @Positive
    private Integer idPost;
}
