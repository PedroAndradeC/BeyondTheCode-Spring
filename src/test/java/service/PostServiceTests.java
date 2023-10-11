package service;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.PostEntity;
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
    public void testAtualizarPost() throws RegraDeNegocioException {

        // setup
        PostDTO postDTO = new PostDTO();

        // comportamento
        when(postRepository.save(any(PostEntity.class))).thenReturn(new PostEntity());

        // act
        PostDTO retorno = postService.atualizarPost(postDTO);

        // assert
        assertNotNull(retorno);
        assertEquals(2, retorno.getIdPost());
        assertEquals("Beyond The Code", retorno.getContents());
        assertEquals("BTC", retorno.getTitle());
    }

}
