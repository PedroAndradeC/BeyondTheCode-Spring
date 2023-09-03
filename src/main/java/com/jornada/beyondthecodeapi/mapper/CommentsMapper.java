package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.CommentsDTO;
import com.jornada.beyondthecodeapi.entity.CommentsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    //DTO para Entity
    CommentsEntity toEntity(CommentsDTO dto);

    //Entity para DTO
    CommentsDTO toDTO(CommentsEntity entity);
}
