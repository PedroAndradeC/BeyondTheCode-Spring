package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDTO salvarUser(UserDTO user) throws RegraDeNegocioException {
        User userConvertido;
        User userSalvo;

        userConvertido = userMapper.converterParaEntity(user);
        userSalvo = userRepository.salvarUserDB(userConvertido);
        UserDTO userReturn = userMapper.converterParaDTO(userSalvo);
        return userReturn;
    }
    public boolean editar(UserDTO user) {
        User userConvertido = userMapper.converterParaEntity(user);
        return userRepository.editar(userConvertido);
    }
    public List<UserDTO> listar() {
        return this.userRepository.listar().stream().map(entidade -> userMapper.converterParaDTO(entidade))
                .toList();
    }

    public boolean excluir(Integer id) {
        return this.userRepository.excluir(id);
    }
}
