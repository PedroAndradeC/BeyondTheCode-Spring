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
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDTO salvarUser(UserDTO user) throws RegraDeNegocioException {
        validarUser(user);
        User userConvertido;
        User userSalvo;

        emailService.enviarEmailComTemplate(user.getEmail(), "Bem vindo ao BeyondTheCode",user.getName());
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

        UserDTO autenticarUser = loginUser(loginRequest);

        if (autenticarUser != null) {
            return autenticarUser; // Autenticação bem-sucedida, retorna o usuário autenticado
        }

        return null; // Senha incorreta ou usuário não encontrado
    }

    public boolean editar(UserDTO user) throws RegraDeNegocioException {
        validarUser(user);
        User userConvertido = userMapper.converterParaEntity(user);
        return userRepository.editar(userConvertido);
    }
    public List<UserDTO> listar() {
        return this.userRepository.listar().stream().map(entidade -> userMapper.converterParaDTO(entidade))
                .toList();
    }

    public void validarUser(UserDTO user) throws RegraDeNegocioException {
        if (!user.getEmail().contains("@gmail") || !user.getEmail().contains("@hotmail")) {
        }else{
        throw new RegraDeNegocioException("Precisa ser @gmail ou @hotmail");
    }
    }

    public boolean excluir(Integer id) {
        return this.userRepository.excluir(id);
    }
}
