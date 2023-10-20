package com.jornada.beyondthecodeapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jornada.beyondthecodeapi.dto.CommunityDTO;
import com.jornada.beyondthecodeapi.dto.CommunityLogDTO;
import com.jornada.beyondthecodeapi.dto.OperacaoCommunity;
import com.jornada.beyondthecodeapi.entity.CommunityEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.mapper.CommunityMapper;
import com.jornada.beyondthecodeapi.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final ProdutorService produtorService;
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public CommunityDTO salvarComunidade(@RequestBody CommunityDTO communityDTO) throws RegraDeNegocioException, JsonProcessingException {

        CommunityEntity entidade = communityMapper.toEntity(communityDTO);
        CommunityEntity salvo = communityRepository.save(entidade);
        CommunityDTO dtoSalvo = communityMapper.toDTO(salvo);

        adicionarLog(communityDTO, "SALVAR");
        return dtoSalvo;
    }

    public CommunityDTO atualizarComunidade(CommunityDTO communityDTO) throws RegraDeNegocioException, JsonProcessingException {

        CommunityEntity entidade = communityMapper.toEntity(communityDTO);
        CommunityEntity salvo = communityRepository.save(entidade);
        CommunityDTO dtoSalvo = communityMapper.toDTO(salvo);

        adicionarLog(communityDTO, "ATUALIZAR");
        return dtoSalvo;
    }

    public List<CommunityDTO> listar() throws JsonProcessingException {
        List<CommunityEntity> listaPostEntities = communityRepository.findAll();
        List<CommunityDTO> list = listaPostEntities.stream()
                .map(entity -> communityMapper.toDTO((CommunityEntity)entity))
                .toList();

        for (CommunityDTO dto : list) {
            adicionarLog(dto, "LISTAR");
        }
        return listaPostEntities.stream().map(communityMapper::toDTO).collect(Collectors.toList());
    }

    private CommunityEntity buscarPorId(String id) throws RegraDeNegocioException {
        return communityRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Comunidade não existe"));
    }

    public void remover(String id) throws RegraDeNegocioException, JsonProcessingException {
        CommunityEntity community = buscarPorId(id);
        communityRepository.delete(community);

        CommunityDTO dto = communityMapper.toDTO(community);
        adicionarLog(dto, "DELETAR");
    }

    public void adicionarLog(CommunityDTO communityDTO, String operacao) throws JsonProcessingException {
        CommunityLogDTO logDTO = new CommunityLogDTO();
        logDTO.setCommunityDTO(communityDTO);
        logDTO.setHorario(new Date());
        logDTO.setOperacaoCommunity(OperacaoCommunity.valueOf(operacao));

        produtorService.EnviarMensagemAoTopico(operacao, logDTO);
    }
}
