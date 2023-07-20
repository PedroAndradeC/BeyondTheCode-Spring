package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.Post;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface PostMapper {
        //DTO para Entity
        Post converterParaEntity(PostDTO dto);

        //Entity para DTO
        PostDTO converterParaDTO(Post entity);
}
