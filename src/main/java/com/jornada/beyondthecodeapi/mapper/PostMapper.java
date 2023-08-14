package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
        //DTO para Entity
        @Mapping(source = "codigoPost",target = "idPost")
        Post toEntity(PostDTO dto);

        //Entity para DTO
        @Mapping(source = "idPost", target = "codigoPost")
        PostDTO toDTO(Post entity);
}
