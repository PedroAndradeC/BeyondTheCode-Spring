package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.PaginaDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDTO salvarUser(UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        emailService.enviarEmailComTemplate(userDTO.getEmail(), "Bem vindo ao BeyondTheCode",userDTO.getName());
        User entidade = userMapper.toEntity(userDTO);
        User salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public UserDTO atualizarUser(@RequestBody UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        User entidade = userMapper.toEntity(userDTO);
        User salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public UserDTO loginUser(UserDTO login) throws RegraDeNegocioException {
        User userLogin = userRepository.findByEmail(login.getEmail());

        if (userLogin != null && userLogin.getPassword().equals(login.getPassword())) {
            return userMapper.toDTO(userLogin);
        } else {
            throw new RegraDeNegocioException("Credenciais inválidas.");
        }
    }

    public UserDTO idUser(Integer id) throws RegraDeNegocioException {
        User entity = buscarIdUser(id);
        return userMapper.toDTO(entity);
    }

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

    public boolean validarIdUser(Integer id) throws RegraDeNegocioException {
        if(userRepository.findById(id) == null){
            throw new RegraDeNegocioException("ID inválido, usuário não existe!");
        }
        return true;
    }

    public void remover(Integer id) {
        userRepository.deleteById(id);
    }

    public PaginaDTO<UserDTO> listarPagina(Integer paginaSolicitada, Integer tamanhoPorPagina){

        PageRequest pageRequest = PageRequest.of(paginaSolicitada,tamanhoPorPagina);
        Page<User> paginaRecuperada = userRepository.findAll(pageRequest);


        return new PaginaDTO<>(paginaRecuperada.getTotalElements(),paginaRecuperada.getTotalPages(),paginaSolicitada,tamanhoPorPagina,paginaRecuperada.getContent().stream()
                .map(entity -> userMapper.toDTO(entity)).toList());
    }

}