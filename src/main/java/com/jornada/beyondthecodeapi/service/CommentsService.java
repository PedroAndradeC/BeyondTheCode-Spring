package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.CommentsDTO;
import com.jornada.beyondthecodeapi.entity.CommentsEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.CommentsMapper;
import com.jornada.beyondthecodeapi.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final CommentsMapper commentsMapper;
    private final PostService postService;

    public CommentsDTO salvarComments(@RequestBody CommentsDTO comments) throws RegraDeNegocioException {
        postService.validarIdPost(comments.getPost().getIdPost());

        CommentsEntity entidade = commentsMapper.toEntity(comments);
        CommentsEntity salvo = commentsRepository.save(entidade);
        CommentsDTO dtoSalvo = commentsMapper.toDTO(salvo);
        return dtoSalvo;

    }

    public CommentsDTO atualizarComments(CommentsDTO comments) throws RegraDeNegocioException {

        CommentsEntity entidade = commentsMapper.toEntity(comments);
        CommentsEntity salvo = commentsRepository.save(entidade);
        CommentsDTO dtoSalvo = commentsMapper.toDTO(salvo);
        return dtoSalvo;

    }

    public List<CommentsDTO> listar() {
        List<CommentsEntity> listaComments = commentsRepository.findAll();
        List<CommentsDTO> dtos = listaComments.stream().map(entity -> commentsMapper.toDTO(entity)).collect(Collectors.toList());
        return dtos;
    }

    public CommentsDTO buscarPorIdDto(Integer id) throws RegraDeNegocioException {
        CommentsEntity entity = buscarPorId(id);
        return commentsMapper.toDTO(entity);
    }

    private CommentsEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        return commentsRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Comentário não existe"));
    }

    public void remover(Integer id) {
        commentsRepository.deleteById(id);
    }

}

