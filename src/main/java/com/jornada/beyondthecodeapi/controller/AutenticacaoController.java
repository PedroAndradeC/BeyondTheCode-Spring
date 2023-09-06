package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.AutenticacaoDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    // Login + token
    public final UserService userService;

    @Operation(summary = "Realiza o login e token do Usuário", description = "Este processo realiza o login e gera um token de um usuário válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping("/login")
    public String fazerLogin(@RequestBody AutenticacaoDTO autenticacaoDTO) throws RegraDeNegocioException{
        return userService.fazerLogin(autenticacaoDTO);
    }

    @Operation(summary = "Retorna o usuário", description = "Este processo retorna o usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping("/usuario-logado")
    public UserDTO recuperarUsuarioLogado() throws RegraDeNegocioException {
        return userService.recuperarUsuarioLogado();
    }
}
