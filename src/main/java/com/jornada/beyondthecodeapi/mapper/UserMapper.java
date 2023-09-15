package com.jornada.beyondthecodeapi.mapper;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.dto.UserRetornoDTO;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
        //DTO para Entity
        UserEntity toEntity(UserDTO dto);

        //Entity para DTO
        UserDTO toDTO(UserEntity entity);

        UserEntity toRetornoEntity(UserRetornoDTO userRetornoDTO);

        UserRetornoDTO toRetornoDTO(UserEntity userEntity);

}
