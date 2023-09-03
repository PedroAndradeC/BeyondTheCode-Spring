package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.AutenticacaoDTO;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    // Login + token
    public final UserService userService;

    @Bean
    @PostMapping("/login")
    public String fazerLogin(@RequestBody AutenticacaoDTO autenticacaoDTO) throws RegraDeNegocioException{
        return userService.fazerLogin(autenticacaoDTO);
    }

//    @GetMapping("/usuario-logado")
//    public User recuperarUsuarioLogado() throws RegraDeNegocioException {
//        return userService.recuperarUsuarioLogado();
//    }

}
