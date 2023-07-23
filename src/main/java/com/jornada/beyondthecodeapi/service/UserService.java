package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import com.jornada.beyondthecodeapi.mapper.exception.RegraDeNegocioException;
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

    public UserDTO loginUser(UserDTO login) {

        User userLogin = userRepository.buscarUsuarioPorEmail(login.getEmail());
        if(userLogin != null) {
            return userMapper.converterParaDTO(userLogin);
        } else {
            return null;
        }
    }
     public UserDTO autenticar(String email, String password) {
        UserDTO loginRequest = new UserDTO();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        UserDTO authenticatedUser = loginUser(loginRequest);

        if (authenticatedUser != null) {
            return authenticatedUser; // Autenticação bem-sucedida, retorna o usuário autenticado
        }

        return null; // Senha incorreta ou usuário não encontrado
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
