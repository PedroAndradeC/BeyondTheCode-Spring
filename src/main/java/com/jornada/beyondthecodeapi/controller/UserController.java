package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.mapper.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @Operation(summary = "Insere novo Usuário", description = "Este processo realiza a inserção de novo Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping
    public UserDTO inserirUsuario(@RequestBody @Valid UserDTO user) throws RegraDeNegocioException {
        return userService.salvarUser(user);
    }

    @PostMapping("/login")
    public UserDTO loginUsuario(@RequestBody @Valid UserDTO user) throws RegraDeNegocioException {
        // Verifica se os campos (email e senha) foram preenchidos
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RegraDeNegocioException("E-mail e senha são obrigatórios para o login.");
        }

        // autenticação q verifica se o usuário existe e a senha está correta
        UserDTO authenticatedUser = userService.autenticar(user.getEmail(), user.getPassword());

        if (authenticatedUser == null) {
            throw new RegraDeNegocioException("Usuário não encontrado ou senha incorreta.");
        }

        return authenticatedUser;
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
    public boolean atualizarUsuario(@RequestBody @Valid UserDTO user) throws RegraDeNegocioException {
        return userService.editar(user);
    }

    @Operation(summary = "Deleta usuarios", description = "Deleta usuarios na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @DeleteMapping("/{id}")
    public boolean remover(@PathVariable("id") Integer id){
        return userService.excluir(id);
    }
}
