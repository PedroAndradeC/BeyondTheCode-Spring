package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface UserMapper {
        //DTO para Entity
        User converterParaEntity(UserDTO dto);

        //Entity para DTO
        UserDTO converterParaDTO(User entity);
}
