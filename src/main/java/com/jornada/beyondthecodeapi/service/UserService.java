package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.*;
import com.jornada.beyondthecodeapi.entity.CargoEntity;
import com.jornada.beyondthecodeapi.entity.UserCargoEntity;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserCargoRepository;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import io.jsonwebtoken.Jwts;


import java.sql.SQLException;
import java.util.*;

@Service
public class UserService {

    private final EmailService emailService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserCargoRepository userCargoRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(@Lazy UserRepository usuarioRepository,
                       @Lazy UserCargoRepository userCargoRepository,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy EmailService emailService,
                       @Lazy UserMapper userMapper) {
        this.userRepository = usuarioRepository;
        this.userCargoRepository = userCargoRepository;
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
            Date dataExpiracao = new Date(dataAtual.getTime() + Long.parseLong(validadeJWT.trim()));

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
            throw new RegraDeNegocioException("E-mail e/ou senha inválidos");
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

        return tokenSpring;
    }


    public UserDTO salvarUser(UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
//        emailService.enviarEmailComTemplate(userDTO.getEmail(), "Bem vindo ao BeyondTheCode", userDTO.getName());
        UserEntity entidade = userMapper.toEntity(userDTO);

        String senha = entidade.getPassword();
        String senhaCriptografada = geradorDeSenha(senha);
        entidade.setPassword(senhaCriptografada);

        entidade.setEnabled(true);

        UserEntity salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public UserDTO atualizarUser(@RequestBody UserDTO userDTO) throws RegraDeNegocioException {
        validarUser(userDTO);
        UserEntity entidade = userMapper.toEntity(userDTO);

        String senha = entidade.getPassword();
        String senhaCriptografada = geradorDeSenha(senha);
        entidade.setPassword(senhaCriptografada);

        UserEntity salvo = userRepository.save(entidade);
        UserDTO dtoSalvo = userMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public String geradorDeSenha(String senha) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = bCryptPasswordEncoder.encode(senha);
        return senhaCriptografada;
    }

//    private void atualizarCargosUsuario(UserEntity userEntity) {
//        // Verifique se a lista de cargos é nula e inicialize-a, se necessário
//        if (userEntity.getCargos() == null) {
//            userEntity.setCargos(new HashSet<>());
//        }
//
//        // Obtenha os cargos que você deseja adicionar ao usuário (por exemplo, Cargo com ID 3)
//        Optional<UserCargoEntity> cargo = userCargoRepository.findById(1); // Certifique-se de que você tenha um repository para Cargos
//
//        // Verifique se o cargo já não está atribuído ao usuário
//        if (!userEntity.getCargos().contains(cargo)) {
//            // Adicione o cargo à lista de cargos do usuário
//            userEntity.getCargos().add(cargo);
//
//            // Atualize o usuário para salvar a relação com o cargo
//            userRepository.save(userEntity);
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

    public List<UserRetornoDTO> listar() {
        List<UserEntity> listaUserEntities = userRepository.findAll();
        List<UserRetornoDTO> dtos = listaUserEntities.stream().map(entity -> userMapper.toRetornoDTO(entity)).toList();
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

    public void remover(Integer id) throws RegraDeNegocioException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        userRepository.delete(user);
    }

    public PaginaDTO<UserDTO> listarUserPagina(Integer paginaSolicitada, Integer tamanhoPorPagina) {

        PageRequest pageRequest = PageRequest.of(paginaSolicitada, tamanhoPorPagina);
        Page<UserEntity> paginaRecuperada = userRepository.findAll(pageRequest);

        return new PaginaDTO<>(paginaRecuperada.getTotalElements(),
                paginaRecuperada.getTotalPages(),
                paginaSolicitada,
                tamanhoPorPagina,
                paginaRecuperada.getContent().stream().map(entity -> userMapper.toDTO(entity)).toList());
    }

    public List<RelatorioUserPostDTO> relatorio() {
        return userRepository.buscarUserPostEComments();
    }

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

    public void desativarUsuario(Integer idInformado) throws RegraDeNegocioException {
        UserEntity entity = userRepository.findById(idInformado).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        entity.setEnabled(false);
        userRepository.save(entity);
    }
}

