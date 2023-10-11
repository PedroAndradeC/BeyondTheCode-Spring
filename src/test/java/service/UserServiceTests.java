package service;

import com.jornada.beyondthecodeapi.dto.AutenticacaoDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.dto.UserRetornoDTO;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.UserMapper;
import com.jornada.beyondthecodeapi.repository.UserRepository;
import com.jornada.beyondthecodeapi.service.EmailService;
import com.jornada.beyondthecodeapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

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
    @Mock
    private Authentication authentication;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(userService, "userMapper", userMapper);
    }

    @Test
    public void deveTestarFazerLoginComSucesso() throws RegraDeNegocioException {
        // Setup
        AutenticacaoDTO autenticacaoDTO = new AutenticacaoDTO();
        UserEntity userEntity = getUserEntity();

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);

        // Act
        String token = userService.fazerLogin(autenticacaoDTO);

        // Assert
        assertNotNull(token);
    }

    @Test
    public void testarSalvarUser() throws RegraDeNegocioException {

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
    public void deveTestarDesativarUsuario() throws RegraDeNegocioException {
        // Setup
        UserEntity userEntity = getUserEntity();
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        // Act
        userService.desativarUsuario(1);

        // Assert
        assertFalse(userEntity.isEnabled());
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
