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

    public UserDTO salvarUser(UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        emailService.enviarEmailComTemplate(userDTO.getEmail(), "Bem vindo ao BeyondTheCode",userDTO.getNome());
        User entidade = userMapper.toEntity(userDTO);
        User salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public UserDTO atualizarUser(UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        User entidade = userMapper.toEntity(userDTO);
        User salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public boolean loginUser(UserDTO login) {

        User userLogin = userRepository.findByEmail(login.getEmail());
        return userLogin.getEmail().equals(login.getEmail()) && userLogin.getPassword().equals(login.getSenha());
//        if(userLogin != null) {
//            return userMapper.toDTO(userLogin);
//        } else {
//            return null;
//        }
    }

    public UserDTO idUser(Integer id) throws RegraDeNegocioException {
        User entity = buscarIdUser(id);
        return userMapper.toDTO(entity);
    }

    //    public UserDTO autenticar(String email, String senha) {
//        UserDTO loginRequest = new UserDTO();
//        loginRequest.setEmail(email);
//        loginRequest.setSenha(senha);
//
//        UserDTO autenticarUser = loginUser(loginRequest);
//
//        if (autenticarUser != null) {
//            return autenticarUser; // Autenticação bem-sucedida, retorna o usuário autenticado
//        }
//
//        return null; // Senha incorreta ou usuário não encontrado
//    }

    public User buscarIdUser(Integer id) throws RegraDeNegocioException {
        return userRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não existe."));
    }

    public List<UserDTO> listar() {
        List<User> listaUsers = userRepository.findAll();
        List<UserDTO> dtos = listaUsers.stream().map(entity -> userMapper.toDTO(entity)).toList();
        return dtos;
    }

    public void validarUser(UserDTO user) throws RegraDeNegocioException {
        if (user.getEmail().contains("@gmail") || user.getEmail().contains("@hotmail") || user.getEmail().contains("@outlook")) {
        }else{
            throw new RegraDeNegocioException("Precisa ser @gmail, @hotmail ou @outlook");
        }
    }

    public void remover(Integer id) {
        userRepository.deleteById(id);
    }
}

