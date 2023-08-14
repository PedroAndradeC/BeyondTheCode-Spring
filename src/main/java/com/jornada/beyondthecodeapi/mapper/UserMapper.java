package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
        //DTO para Entity
        @Mapping(source = "codigoUser", target = "id")
        @Mapping(source = "senha", target = "password")
        @Mapping(source = "nome", target = "name")
        User toEntity(UserDTO dto);

        //Entity para DTO
        @Mapping(source = "id", target = "codigoUser")
        @Mapping(source = "password", target = "senha")
        @Mapping(source = "name", target = "nome")
        UserDTO toDTO(User entity);
}
