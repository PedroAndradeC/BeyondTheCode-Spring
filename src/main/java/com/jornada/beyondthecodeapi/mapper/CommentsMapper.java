package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.CommentsDTO;
import com.jornada.beyondthecodeapi.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    //DTO para Entity
    Comments toEntity(CommentsDTO dto);

    //Entity para DTO
    CommentsDTO toDTO(Comments entity);
}
