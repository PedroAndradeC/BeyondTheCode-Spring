package com.jornada.beyondthecodeapi.service;

import com.jornada.beyondthecodeapi.dto.CommunityDTO;
import com.jornada.beyondthecodeapi.entity.CommunityEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.CommunityMapper;
import com.jornada.beyondthecodeapi.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public CommunityDTO salvarComunidade(@RequestBody CommunityDTO communityDTO) throws RegraDeNegocioException {

        CommunityEntity entidade = communityMapper.toEntity(communityDTO);
        CommunityEntity salvo = communityRepository.save(entidade);
        CommunityDTO dtoSalvo = communityMapper.toDTO(salvo);
        return dtoSalvo;
    }

    public CommunityDTO atualizarComunidade(CommunityDTO communityDTO) throws RegraDeNegocioException {

        CommunityEntity entidade = communityMapper.toEntity(communityDTO);
        CommunityEntity salvo = communityRepository.save(entidade);
        CommunityDTO dtoSalvo = communityMapper.toDTO(salvo);
        return dtoSalvo;

    }

    public List<CommunityDTO> listar() {
        List<CommunityEntity> listaPostEntities = communityRepository.findAll();
        return listaPostEntities.stream().map(communityMapper::toDTO).collect(Collectors.toList());
    }
//
//    public boolean validarIdPost(Integer id) throws RegraDeNegocioException {
//        if(postRepository.findById(id) == null){
//            throw new RegraDeNegocioException("ID inválido, Post não existe!");
//        }
//        return true;
//    }
    private CommunityEntity buscarPorId(String id) throws RegraDeNegocioException {
        return communityRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Comunidade não existe"));
    }

    public void remover(String id) {
        communityRepository.deleteById(id);
    }
}
