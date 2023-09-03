package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
        //DTO para Entity
//        @Mapping(source = "codigoPost",target = "idPost")
        PostEntity toEntity(PostDTO dto);

        //Entity para DTO
//        @Mapping(source = "idPost", target = "codigoPost")
        PostDTO toDTO(PostEntity entity);
}
