package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.Post;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.PostMapper;
import com.jornada.beyondthecodeapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final UserService userService;
    private final PostRepository postRepository;

    public PostDTO salvarPost(PostDTO post) throws RegraDeNegocioException {

        Post postConvertido = postMapper.converterParaEntity(post);
        Post postSalvo = postRepository.salvarPost(postConvertido);
        PostDTO postReturn = postMapper.converterParaDTO(postSalvo);
        return postReturn;
    }

    public boolean editar(PostDTO post) throws RegraDeNegocioException {
        autenticarPost(post);
        Post postConvertido = postMapper.converterParaEntity(post);
        return postRepository.editar(postConvertido);
    }
    public List<PostDTO> listar() {
        return this.postRepository.listar().stream()
                .map(entidade -> postMapper.converterParaDTO(entidade))
                .toList();
    }

    public PostDTO autenticarPost(PostDTO post) throws RegraDeNegocioException {
        Post postConvertido;
        int idUsuario;

        postConvertido = postMapper.converterParaEntity(post);
        idUsuario = postRepository.buscarPostID(postConvertido.getIdPost());
        PostDTO postReturn = postMapper.converterParaDTO(postConvertido);
        if (idUsuario == post.getIdUser()) {
            return post;
        } else {
            throw new RegraDeNegocioException("Id Inválido");
        }
    }

    public boolean excluir(Integer idPost, Integer idUser) throws RegraDeNegocioException {
        int idUsuario = postRepository.buscarPostID(idPost);
        if(idUsuario == idUser){
            return this.postRepository.excluir(idPost);
        }else{
            throw new RegraDeNegocioException("Usuário Inválido");
        }
    }
}
