package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.AutenticacaoDTO;
import com.jornada.beyondthecodeapi.dto.PaginaDTO;
import com.jornada.beyondthecodeapi.dto.RelatorioUserPostDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.User;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import io.jsonwebtoken.Jwts;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final EmailService emailService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public UserService(@Lazy UserRepository usuarioRepository, @Lazy AuthenticationManager authenticationManager, @Lazy EmailService emailService, @Lazy UserMapper userMapper) {
        this.userRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.userMapper = userMapper;

    }

    @Value("${jwt.validade.token}")
    private String validadeJWT;

    @Value("${jwt.secrete}")
    private String secret;


    public UserDTO salvarUser(UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        emailService.enviarEmailComTemplate(userDTO.getEmail(), "Bem vindo ao BeyondTheCode", userDTO.getName());
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
        } else {
            throw new RegraDeNegocioException("Precisa ser @gmail, @hotmail ou @outlook");
        }
    }

    public boolean validarIdUser(Integer id) throws RegraDeNegocioException {
        if (userRepository.findById(id) == null) {
            throw new RegraDeNegocioException("ID inválido, usuário não existe!");
        }
        return true;
    }

    public void remover(Integer id) {
        userRepository.deleteById(id);
    }

    public PaginaDTO<UserDTO> listarUserPagina(Integer paginaSolicitada, Integer tamanhoPorPagina) {

        PageRequest pageRequest = PageRequest.of(paginaSolicitada, tamanhoPorPagina);
        Page<User> paginaRecuperada = userRepository.findAll(pageRequest);


        return new PaginaDTO<>(paginaRecuperada.getTotalElements(), paginaRecuperada.getTotalPages(), paginaSolicitada, tamanhoPorPagina, paginaRecuperada.getContent().stream()
                .map(entity -> userMapper.toDTO(entity)).toList());
    }

    public List<RelatorioUserPostDTO> relatorio() {
        return userRepository.buscarUserPostEComments();
    }


    public UsernamePasswordAuthenticationToken validarToken(String token){
        if (token == null) {
            return null;
        }

        String tokenClean = token.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .setSigningKey(secret) //utiliza a secret
                .parseClaimsJws(tokenClean) //decriptografa e valida o token...
                .getBody(); //recupera o payload

        String idUsuario = claims.getSubject();

//        Optional<User> userDTOOptional = userRepository.findById(Integer.parseInt(idUsuario));

        UsernamePasswordAuthenticationToken tokenSpring = new UsernamePasswordAuthenticationToken(idUsuario,null);



//        return userDTOOptional.orElseThrow(() -> new RegraDeNegocioException("Usuário e/ou senha inválidos!"));
        return tokenSpring;

    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public String fazerLogin(AutenticacaoDTO autenticacaoDTO) throws RegraDeNegocioException {

        UsernamePasswordAuthenticationToken dtoSpring = new UsernamePasswordAuthenticationToken(
                autenticacaoDTO.getEmail(),
                autenticacaoDTO.getSenha()
        );

        try {
            Authentication autenticacao = authenticationManager.authenticate(dtoSpring);

            Object usuarioAutenticado = autenticacao.getPrincipal();
            UserDTO userDTO = (UserDTO) usuarioAutenticado;



            Date dataAtual = new Date();
            Date dataExpiracao = new Date(dataAtual.getTime() + Long.parseLong(validadeJWT));

            // ['cargo1, cargo2, cargo3']
            // 1 dia
            String jwtGerado = Jwts.builder()
                    .setIssuer("beyondethecode-api")
                    .setSubject(userDTO.getId().toString())
                    .setIssuedAt(dataAtual)
                    .setExpiration(dataExpiracao)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();

            return jwtGerado;

        } catch (AuthenticationException ex) {
            throw new RegraDeNegocioException("Usuario e senha inválidos");
        }
    }
}

