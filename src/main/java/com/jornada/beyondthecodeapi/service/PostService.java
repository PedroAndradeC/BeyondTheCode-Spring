package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.PaginaDTO;
import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.PostEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.PostMapper;
import com.jornada.beyondthecodeapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final UserService userService;
    private final PostRepository postRepository;

    public PostDTO salvarPost(@RequestBody PostDTO post) throws RegraDeNegocioException {
        userService.validarIdUser(post.getUser().getId());

        PostEntity entidade = postMapper.toEntity(post);
        PostEntity salvo = postRepository.save(entidade);
        PostDTO dtoSalvo = postMapper.toDTO(salvo);
        return dtoSalvo;

    }

    public PostDTO atualizarPost(PostDTO post) throws RegraDeNegocioException {

        PostEntity entidade = postMapper.toEntity(post);
        PostEntity salvo = postRepository.save(entidade);
        PostDTO dtoSalvo = postMapper.toDTO(salvo);
        return dtoSalvo;

    }

    public List<PostDTO> listar() {
        List<PostEntity> listaPostEntities = postRepository.findAll();
        List<PostDTO> dtos = listaPostEntities.stream().map(entity -> postMapper.toDTO(entity)).collect(Collectors.toList());
        return dtos;
    }

    public boolean validarIdPost(Integer id) throws RegraDeNegocioException {
        if(postRepository.findById(id) == null){
            throw new RegraDeNegocioException("ID inválido, Post não existe!");
        }
        return true;
    }
    private PostEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        return postRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Post não existe"));
    }

    public void remover(Integer id) {
        postRepository.deleteById(id);
    }

    public PaginaDTO<PostDTO> listarPostPaginado(Integer paginaSolicitada, Integer tamanhoPorPagina){

        PageRequest pageRequest = PageRequest.of(paginaSolicitada,tamanhoPorPagina);
        Page<PostEntity> paginaRecuperada = postRepository.findAll(pageRequest);

        return new PaginaDTO<>(paginaRecuperada.getTotalElements(),paginaRecuperada.getTotalPages(),paginaSolicitada,tamanhoPorPagina,paginaRecuperada.getContent().stream()
                .map(entity -> postMapper.toDTO(entity)).toList());
    }

}
