package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.Post;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.PostMapper;
import com.jornada.beyondthecodeapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final UserService userService;
    private final PostRepository postRepository;

    public PostDTO salvarPost(@RequestBody PostDTO post) throws RegraDeNegocioException {
        userService.validarIdUser(post.getUser().getId());

        Post entidade = postMapper.toEntity(post);
        Post salvo = postRepository.save(entidade);
        PostDTO dtoSalvo = postMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public PostDTO atualizarPost(PostDTO post) throws RegraDeNegocioException {

        Post entidade = postMapper.toEntity(post);
        Post salvo = postRepository.save(entidade);
        PostDTO dtoSalvo = postMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public List<PostDTO> listar() {
        List<Post> listaPosts = postRepository.findAll();
        List<PostDTO> dtos = listaPosts.stream().map(entity -> postMapper.toDTO(entity)).collect(Collectors.toList());
        return dtos;
    }

    public PostDTO buscarPorIdDto(Integer id) throws RegraDeNegocioException {
        Post entity = buscarPorId(id);
        return postMapper.toDTO(entity);
    }

    private Post buscarPorId(Integer id) throws RegraDeNegocioException {
        return postRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Post não existe"));
    }
//    public PostDTO autenticarPost(PostDTO post) throws RegraDeNegocioException {
//        Post postConvertido = postMapper.toEntity(post);
//
//        Optional<Post> postOptional = postRepository.findById(postConvertido.getIdPost());
//
//        if (postOptional.isPresent()) {
//            Post postEncontrado = postOptional.get();
//            if (postEncontrado.getUser() == postConvertido.getUser()) {
//                return post;
//            } else {
//                throw new RegraDeNegocioException("Id Inválido");
//            }
//        } else {
//            throw new RegraDeNegocioException("Post não encontrado");
//        }
//    }

//    public PostDTO autenticarPost(PostDTO post) throws RegraDeNegocioException {
//        Post postConvertido;
//        int idUsuario;
//
//        postConvertido = postMapper.toEntity(post);
//        idUsuario = postRepository.buscarPostID(postConvertido.getIdPost());
//        PostDTO postReturn = postMapper.toDTO(postConvertido);
//        if (idUsuario == post.getIdUser()) {
//            return post;
//        } else {
//            throw new RegraDeNegocioException("Id Inválido");
//        }
//    }

    public void remover(Integer id) {
        postRepository.deleteById(id);
    }
}
