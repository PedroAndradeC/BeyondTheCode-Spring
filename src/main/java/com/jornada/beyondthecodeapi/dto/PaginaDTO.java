package com.jornada.beyondthecodeapi.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginaDTO<E> {

    private long totalElementos;
    private Integer totalPaginas;
    private Integer pagina;
    private Integer tamanho;
    private List<E> elementos;
}
