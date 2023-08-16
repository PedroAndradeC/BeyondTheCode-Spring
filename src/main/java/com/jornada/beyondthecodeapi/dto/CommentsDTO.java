package com.jornada.beyondthecodeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CommentsDTO {

    @Schema(description = "codigo identificador de comentario", example = "5")
    @Positive
    private Integer idComment;

    @Schema(description = "colocar conteudo do comentario", example = "Lorem Ipsum Sit Dolor Amet")
    @NotBlank
    @NotNull
    private String contents;

    private PostDTO post;
}
