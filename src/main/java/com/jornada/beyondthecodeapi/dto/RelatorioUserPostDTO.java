package com.jornada.beyondthecodeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioUserPostDTO {
    private Integer id;
    private String name;
    private Integer idPost;
    private String title;
    private Integer idComment;
    private String contents;
}
