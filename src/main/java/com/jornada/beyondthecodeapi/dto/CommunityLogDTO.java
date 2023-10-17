package com.jornada.beyondthecodeapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommunityLogDTO {

    private CommunityDTO communityDTO;
    private Date horario;
}
