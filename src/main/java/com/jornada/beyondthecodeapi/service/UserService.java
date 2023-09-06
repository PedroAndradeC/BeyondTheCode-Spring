package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.AutenticacaoDTO;
import com.jornada.beyondthecodeapi.dto.PaginaDTO;
import com.jornada.beyondthecodeapi.dto.RelatorioUserPostDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import io.jsonwebtoken.Jwts;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final EmailService emailService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(@Lazy UserRepository usuarioRepository,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy EmailService emailService,
                       @Lazy UserMapper userMapper) {
        this.userRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    @Value("${jwt.validade.token}")
    private String validadeJWT;

    @Value("${jwt.secret}")
    private String secret;


    public String fazerLogin(AutenticacaoDTO autenticacaoDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken dtoDoSpring = new UsernamePasswordAuthenticationToken(
                autenticacaoDTO.getEmail(),
                autenticacaoDTO.getPassword()
        );
        try {
            Authentication autenticacao = authenticationManager.authenticate(dtoDoSpring);

            Object usuarioAutenticado = autenticacao.getPrincipal();
            UserEntity userEntity = (UserEntity) usuarioAutenticado;

            List<String> nomeDosCargos= userEntity.getCargos().stream()
                    .map(cargo -> cargo.getNome()).toList();

            Date dataAtual = new Date();
            Date dataExpiracao = new Date(dataAtual.getTime() + Long.parseLong(validadeJWT));

            String jwtGerado = Jwts.builder()
                    .setIssuer("beyondthecode-api")
                    .claim("CARGOS", nomeDosCargos)
                    .setSubject(userEntity.getId().toString())
                    .setIssuedAt(dataAtual)
                    .setExpiration(dataExpiracao)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();

            return jwtGerado;

        } catch (AuthenticationException ex) {
            throw new RegraDeNegocioException("Usuario e senha inválidos");
        }
    }

    public UsernamePasswordAuthenticationToken validarToken(String token) {
        if (token == null) {
            return null;
        }
        String tokenLimpo = token.replace("Bearer ", "");

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(tokenLimpo)
                .getBody();

        String idUser = claims.getSubject();
        List<String> cargos = claims.get("CARGOS", List.class);

        List<SimpleGrantedAuthority> listaDeCargos = cargos.stream()
                .map(cargoStr -> new SimpleGrantedAuthority(cargoStr))
                .toList();

        UsernamePasswordAuthenticationToken tokenSpring
                = new UsernamePasswordAuthenticationToken(idUser, null, listaDeCargos);

//        Optional<UserEntity> userEntityOptional = userRepository.findById(Integer.parseInt(subject));
        return tokenSpring;
    }


    public UserDTO salvarUser(UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        emailService.enviarEmailComTemplate(userDTO.getEmail(), "Bem vindo ao BeyondTheCode", userDTO.getName());
        UserEntity entidade = userMapper.toEntity(userDTO);
        UserEntity salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public UserDTO atualizarUser(@RequestBody UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        UserEntity entidade = userMapper.toEntity(userDTO);
        UserEntity salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

//    public UserDTO loginUser(UserDTO login) throws RegraDeNegocioException {
//        UserEntity userEntityLogin = userRepository.findByEmail(login.getEmail());
//
//        if (userEntityLogin != null && userEntityLogin.getPassword().equals(login.getPassword())) {
//            return userMapper.toDTO(userEntityLogin);
//        } else {
//            throw new RegraDeNegocioException("Credenciais inválidas.");
//        }
//    }



    public UserDTO idUser(Integer id) throws RegraDeNegocioException {
        UserEntity entity = buscarIdUser(id);
        return userMapper.toDTO(entity);
    }

    public UserEntity buscarIdUser(Integer id) throws RegraDeNegocioException {
        return userRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não existe."));
    }

    public List<UserDTO> listar() {
        List<UserEntity> listaUserEntities = userRepository.findAll();
        List<UserDTO> dtos = listaUserEntities.stream().map(entity -> userMapper.toDTO(entity)).toList();
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
        Page<UserEntity> paginaRecuperada = userRepository.findAll(pageRequest);


        return new PaginaDTO<>(paginaRecuperada.getTotalElements(), paginaRecuperada.getTotalPages(), paginaSolicitada, tamanhoPorPagina, paginaRecuperada.getContent().stream()
                .map(entity -> userMapper.toDTO(entity)).toList());
    }

    public List<RelatorioUserPostDTO> relatorio() {
        return userRepository.buscarUserPostEComments();
    }

//    public UsernamePasswordAuthenticationToken validarToken(String token){
//        if (token == null) {
//            return null;
//        }
//
//        String tokenClean = token.replace("Bearer ", "");
//        Claims claims = Jwts.parser()
//                .setSigningKey(secret) //utiliza a secret
//                .parseClaimsJws(tokenClean) //decriptografa e valida o token...
//                .getBody(); //recupera o payload
//
//        String idUsuario = claims.getSubject();
//
////        Optional<User> userDTOOptional = userRepository.findById(Integer.parseInt(idUsuario));
//
//        UsernamePasswordAuthenticationToken tokenSpring = new UsernamePasswordAuthenticationToken(idUsuario,null);
//
////        return userDTOOptional.orElseThrow(() -> new RegraDeNegocioException("Usuário e/ou senha inválidos!"));
//        return tokenSpring;
//
//    }

    public Integer recuperarIdUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object idUsuario = authentication.getPrincipal();
        String idUsuarioString = (String) idUsuario;
        return Integer.parseInt(idUsuarioString);
    }

    public UserDTO recuperarUsuarioLogado() throws RegraDeNegocioException {
        Integer idUsuarioLogado = recuperarIdUsuarioLogado();
        UserEntity entity = userRepository.findById(idUsuarioLogado).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        return userMapper.toDTO(entity);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}

