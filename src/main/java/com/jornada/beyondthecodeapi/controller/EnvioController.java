package com.jornada.beyondthecodeapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jornada.beyondthecodeapi.dto.CommunityDTO;
import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.service.ProdutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class EnvioController {

    private final ProdutorService produtorService;

    @PostMapping
    public void enviarMensagem(@RequestBody CommunityDTO communityDTO) throws JsonProcessingException {
        produtorService.EnviarMensagemAoTopico(communityDTO, communityDTO.getDescriptionCommunity().ordinal());
    }

}
