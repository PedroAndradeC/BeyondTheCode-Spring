package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
        //DTO para Entity
//        @Mapping(source = "codigoUser", target = "id")
//        @Mapping(source = "senha", target = "password")
        User converterParaEntity(UserDTO dto);

        //Entity para DTO
//        @Mapping(source = "id", target = "codigoUser")
//        @Mapping(source = "password", target = "senha")
        UserDTO converterParaDTO(User entity);
}
