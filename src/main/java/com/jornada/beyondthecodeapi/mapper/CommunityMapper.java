package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.CommunityDTO;
import com.jornada.beyondthecodeapi.entity.CommunityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityMapper {

    // DTO para Entity
    CommunityEntity toEntity(CommunityDTO dto);

    // Entity para DTO
    CommunityDTO toDTO(CommunityEntity entity);
}
