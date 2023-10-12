package service;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.entity.PostEntity;
import com.jornada.beyondthecodeapi.entity.UserEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.PostMapper;
import com.jornada.beyondthecodeapi.repository.PostRepository;
import com.jornada.beyondthecodeapi.service.PostService;
import com.jornada.beyondthecodeapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    @InjectMocks
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(postService, "postMapper", postMapper);
    }

    @Test
    public void TestarSalvarOuAtualizarComSucesso() throws RegraDeNegocioException {
        //setup

        PostDTO dto = getPostDTO();
        PostEntity entity = getPostEntity();

        //comportamentos
        when(postRepository.save(any())).thenReturn(entity);

        //act
        PostDTO retorno = postService.salvarPost(dto);

        //assert
        assertNotNull(retorno);
        assertEquals(2, retorno.getIdPost());
        assertEquals("Beyond The Code", retorno.getContents());
        assertEquals("BTC", retorno.getTitle());
        assertEquals(getUserDTO(), retorno.getUser());
    }

    @Test
    public void deveTestarListarComSucesso() {
        //setup
        PostEntity tarefaEntity = getPostEntity();
        List<PostEntity> listaEntities = List.of(tarefaEntity);
        when(postRepository.findAll()).thenReturn(listaEntities);

        //act
        List<PostDTO> lista = postService.listar();

        //assert
        assertNotNull(lista);
        assertEquals(1,lista.size());
    }

    private static UserDTO getUserDTO(){
        UserDTO dto = new UserDTO();
        dto.setId(2);
        dto.setName("Fulano");
        dto.setPassword("12345");
        dto.setEmail("fulano@gmail.com");
        return dto;
    }

    private static UserEntity getUserEntity(){
        UserEntity entity = new UserEntity();
        entity.setId(2);
        entity.setName("Fulano");
        entity.setPassword("12345");
        entity.setEmail("fulano@gmail.com");
        entity.setEnabled(true);
        return entity;
    }
}
