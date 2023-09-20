package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.CommunityDTO;
import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.entity.CommunityEntity;
import com.jornada.beyondthecodeapi.entity.CommunitysPerTopicEntity;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.repository.CommunityRepository;
import com.jornada.beyondthecodeapi.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityRepository communityRepository;

    @Operation(summary = "Insere novo Comunidade", description = "Este processo realiza a inserção de nova Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping
    public CommunityDTO inserirComunidade(@RequestBody @Valid CommunityDTO communityDTO) throws RegraDeNegocioException {
        log.info("Comunidade foi inserida");
        return communityService.salvarComunidade(communityDTO);
    }

    @Operation(summary = "Retorna todos as Comunidades", description = "Este processo retorna todos as comunidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping
    public List<CommunityDTO> retornarTodasComunidades(){
        return communityService.listar();
    }

    @Operation(summary = "Atualiza Comunidade", description = "Atualiza uma comunidade na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PutMapping
    public CommunityDTO atualizarComunidade(@RequestBody @Valid CommunityDTO communityDTO) throws RegraDeNegocioException {
        return communityService.atualizarComunidade(communityDTO);
    }

    @Operation(summary = "Deleta comunidade", description = "Deleta a comunidade na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @DeleteMapping
    public void remover(String id) {
        communityService.remover(id);
    }

    @GetMapping("/findByCommunityTopics")
    public List<CommunitysPerTopicEntity> findByCommunityTopics(String topico){
        return communityRepository.findByCommunityTopics(topico);
    }

}
