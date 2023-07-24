package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.Post;
import com.jornada.beyondthecodeapi.mapper.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.PostMapper;
import com.jornada.beyondthecodeapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostDTO salvarPost(PostDTO post) throws RegraDeNegocioException {
        Post postConvertido;
        Post postSalvo;

        postConvertido = postMapper.converterParaEntity(post);
        postSalvo = postRepository.salvarPost(postConvertido);
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
            throw new RegraDeNegocioException("Id inválido");
        } else {
            throw new RegraDeNegocioException("Id Correto");
        }
    }

    public boolean excluir(Integer id) {
        return this.postRepository.excluir(id);
    }
}
