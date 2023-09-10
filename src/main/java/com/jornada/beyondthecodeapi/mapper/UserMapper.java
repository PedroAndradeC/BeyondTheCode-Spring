package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.dto.UserRetornoDTO;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
        //DTO para Entity
//        @Mapping(source = "id", target = "id")
//        @Mapping(source = "senha", target = "password")
//        @Mapping(source = "name", target = "name")
        UserEntity toEntity(UserDTO dto);

        //Entity para DTO
//        @Mapping(source = "id", target = "id")
//        @Mapping(source = "password", target = "senha")
//        @Mapping(source = "name", target = "name")
        UserDTO toDTO(UserEntity entity);

        UserEntity toRetornoEntity(UserRetornoDTO userRetornoDTO);

        UserRetornoDTO toRetornoDTO(UserEntity userEntity);

}
