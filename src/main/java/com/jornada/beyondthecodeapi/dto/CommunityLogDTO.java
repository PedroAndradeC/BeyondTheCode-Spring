package com.jornada.beyondthecodeapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommunityLogDTO {

    private String idCommunity;
    private OperacaoCommunity operacaoCommunity;
    private CommunityDTO communityDTO;
    private Date horario;
}
