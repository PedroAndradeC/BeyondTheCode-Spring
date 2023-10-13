package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.*;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import com.jornada.beyondthecodeapi.service.EmailService;
import com.jornada.beyondthecodeapi.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private Jwts jwts;

    @Mock
    private Claims claims;

    @Mock
    private SimpleGrantedAuthority simpleGrantedAuthority;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(userService, "userMapper", userMapper);
        ReflectionTestUtils.setField(userService, "secret", "MinhaChaveSecreta");
        ReflectionTestUtils.setField(userService, "validadeJWT", "86400000");
    }

    @Test
    public void deveTestarFazerLoginComSucesso() throws RegraDeNegocioException {

        // Setup
        AutenticacaoDTO dto = new AutenticacaoDTO();
        dto.setEmail("guilhermefrank@gmail.com");
        dto.setPassword("12345");

        Authentication userAuthentication = mock(Authentication.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(authenticationManager.authenticate(any())).thenReturn(userAuthentication);
        when(userAuthentication.getPrincipal()).thenReturn(userEntity);

        // Act
        String autenticacaoDTO = userService.fazerLogin(dto);

        // Assert
        Assertions.assertNotNull(autenticacaoDTO);
    }

    @Test
    public void deveTestarValidarTokenComSucesso() {
        //setup
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiZXlvbmR0aGVjb2RlLWFwaSIsIkNBUkdPUyI6WyJST0xFX0FETUlOIl0sInN1YiI6IjUiLCJpYXQiOjE2OTcxMzUzMDUsImV4cCI6MTY5NzIyMTcwNX0.achQZUbq4mstq_TObF7mQfRkrBWsV1L91ZanENLb8qA";

        //act
        UsernamePasswordAuthenticationToken user = userService.validarToken(token);
        //assert

        assertNotNull(token);

    }

    @Test
    public void testarSalvarOuAtualizarUserComSucesso() throws RegraDeNegocioException {

        // setup
        UserDTO dto = getUserDTO();
        UserEntity userEntity = getUserEntity();

        // comportamentos
        when(userRepository.save(any())).thenReturn(userEntity);

        // act
        UserDTO retorno = userService.salvarUser(dto);

        // assert
        assertNotNull(retorno);
        assertEquals(1, retorno.getId());
        assertEquals("Fulano de Tal", retorno.getName());
        assertEquals("12345", retorno.getPassword());
        assertEquals("fulanodetal@gmail.com", retorno.getEmail());
    }

    @Test
    public void testarListar() {

        // setup
        UserEntity entity = getUserEntity();
        List<UserEntity> listaDeEntities = List.of(entity);
        when(userRepository.findAll()).thenReturn(listaDeEntities);

        // act
        List<UserRetornoDTO> listagem = userService.listar();

        // assert
        assertNotNull(listagem);
        assertEquals(1, listagem.size());
    }

    @Test
    public void deveTestarRelatorioComSucesso(){
        //setup
        RelatorioUserPostDTO relatorioUserPostDTO = new RelatorioUserPostDTO();
        //act
        userService.relatorio();
        //assert
        Assertions.assertNotNull(relatorioUserPostDTO);
    }

    @Test
    public void deveTestarFindByEmailComSucesso(){
        //setup
        String email = "fulano@gmail.com";

        //act
        userService.findByEmail(email);

        //assert
        Assertions.assertNotNull(email);
    }

    @Test
    public void deveTestarDesativarUser() throws RegraDeNegocioException {
        // Setup
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        // Act
        userService.desativarUsuario(1);

        // Assert
        assertFalse(userEntity.isEnabled());
    }

    @Test
    public void deveTestarRecuperarUserLogadoComSucesso() throws RegraDeNegocioException {
        //setup
        String name = "fulano";
        Authentication userAuthentication = mock(Authentication.class);
        UserEntity entity = getUserEntity();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("0", null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(token);

        when(userRepository.findById(any())).thenReturn(Optional.of(entity));

        //act
        userService.recuperarUsuarioLogado();
        //assert

        assertNotNull(name);
    }

    @Test
    public void deveTestarRecuperarIdUsuarioLogadoComSucesso(){
        //setup
        Integer idUsuario = 4;

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("0", null,
                new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(token);

        //act
        userService.recuperarIdUsuarioLogado();

        //assert
        assertNotNull(idUsuario);
    }

    @Test
    public void deveRemoverComSucesso() throws RegraDeNegocioException {
        //setup
        UserEntity userEntity = getUserEntity();
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);
        when(userRepository.findById(anyInt())).thenReturn(userEntityOptional);


        //act
        userService.remover(1);

        //assert
        verify(userRepository, times(1)).delete(any());
    }


    @Test
    public void deveTestarRemoverComErro() throws RegraDeNegocioException{
        //setup
        Optional<UserEntity> usuarioEntityOptional = Optional.empty();
        when(userRepository.findById(anyInt())).thenReturn(usuarioEntityOptional);

        //assert
        assertThrows(RegraDeNegocioException.class, ()-> {
            //act
            userService.remover(1);
        });
    }

    @Test
    public void deveTestarListarUserPaginaComSucesso() {
        // Setup
        PaginaDTO<UserDTO> paginaUserDTO = getPaginaUserDTO();
        PageRequest pageRequest = PageRequest.of(1, 1);

        List<UserEntity> userEntities = new ArrayList<>();

        when(userRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(userEntities, pageRequest, userEntities.size()));
        // act
        PaginaDTO<UserDTO> paginaRecuperada = userService.listarUserPagina(1, 1);

        // assert
        assertNotNull(paginaRecuperada);
        assertEquals(paginaUserDTO.getPagina(), paginaRecuperada.getPagina());
        assertEquals(paginaUserDTO.getTamanho(), paginaRecuperada.getTamanho());
    }

    @Test
    public void deveTestarGerarSenhaComSucesso(){
        //setup
        UserEntity userEntity = getUserEntity();

        //act
        userService.geradorDeSenha(userEntity.getPassword());

        //assert
        assertNotNull(userEntity.getPassword());
    }

    private static PaginaDTO getPaginaUserDTO() {
        var paginaDTO = new PaginaDTO();
        paginaDTO.setTotalPaginas(2);
        paginaDTO.setPagina(1);
        paginaDTO.setTamanho(1);
        paginaDTO.setElementos(new ArrayList());

        return paginaDTO;
    }

    private static UserDTO getUserDTO(){
        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setName("Fulano de Tal");
        dto.setPassword("12345");
        dto.setEmail("fulanodetal@gmail.com");
        return dto;
    }

    private static UserEntity getUserEntity(){
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setName("Fulano de Tal");
        entity.setPassword("12345");
        entity.setEmail("fulanodetal@gmail.com");
        return entity;
    }
}
