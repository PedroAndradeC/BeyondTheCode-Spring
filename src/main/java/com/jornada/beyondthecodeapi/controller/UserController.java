package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "Insere novo Usuário", description = "Este processo realiza a inserção de novo Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping
    public UserDTO inserirVendedor(@RequestBody UserDTO user) throws RegraDeNegocioException {
        return userService.salvarUser(user);
    }

    @Operation(summary = "Retorna todos os usuários", description = "Este processo retorna todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping
    public List<UserDTO> retornarTodosOsUsuarios(){
        return userService.listar();
    }

    @Operation(summary = "Atualiza usuários", description = "Atualiza usuários na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PutMapping
    public boolean atualizarVendedor(@RequestBody UserDTO user) throws RegraDeNegocioException {
        return userService.editar(user);
    }

    @Operation(summary = "Deleta usuarios", description = "Deleta usuarios na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @DeleteMapping("/{idVendedor}")
    public boolean remover(@PathVariable("idVendedor") Integer id){
        return userService.excluir(id);
    }
}
