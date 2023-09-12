package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.PaginaDTO;
import com.jornada.beyondthecodeapi.dto.RelatorioUserPostDTO;
import com.jornada.beyondthecodeapi.dto.UserDTO;
import com.jornada.beyondthecodeapi.dto.UserRetornoDTO;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.EmailService;
import com.jornada.beyondthecodeapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Value("${ambiente.api.nome}")
    private String NomeApi;
    private final UserService userService;
    private final EmailService emailService;

    @Operation(summary = "Insere novo Usuário", description = "Este processo realiza a inserção de novo Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping
    public UserDTO inserirUsuario(@RequestBody @Valid UserDTO user) throws RegraDeNegocioException {
        log.info("Usuario foi inserido");
        return userService.salvarUser(user);
    }

    @Operation(summary = "Envia email para um Usuário", description = "Este processo realiza o envio de um email automático")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping("/EnviarEmail")
    public void Email(String para, String assunto, String nome) throws MessagingException {
        this.emailService.enviarEmailComTemplate(para, assunto, nome);
    }

    @Operation(summary = "Retorna todos os usuários", description = "Este processo retorna todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping
    public List<UserRetornoDTO> retornarTodosOsUsuarios(){
        return userService.listar();
    }

    @Operation(summary = "Atualiza usuários", description = "Atualiza usuários na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PutMapping
    public UserDTO atualizarUsuario(@RequestBody @Valid UserDTO user) throws RegraDeNegocioException {
        return userService.atualizarUser(user);
    }

    @Operation(summary = "Retorna um usuário por id", description = "Este processo retorna um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping("/por-id")
    public UserDTO buscarPorId(Integer id) throws RegraDeNegocioException {
        return userService.idUser(id);
    }

    @Operation(summary = "Deleta usuarios", description = "Deleta usuarios na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @DeleteMapping("/{id}")
    public void remover(@PathVariable("id") Integer id){
        userService.remover(id);
    }

    @Operation(summary = "Retorna a paginação de User", description = "Este processo retorna a paginação de User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping("/listarUserPaginado")
    public PaginaDTO<UserDTO> listarPagina (Integer paginaSolicitada, Integer tamanhoPorPagina){
        return userService.listarUserPagina(paginaSolicitada,tamanhoPorPagina);
    }

    @Operation(summary = "Retorna a paginação o Relatório (Consulsta Personalizada)", description = "Este processo retorna a paginação de User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping("/relatorio")
    public List<RelatorioUserPostDTO> gerarRelatorio() {
        return userService.relatorio();
    }

    @Operation(summary = "Desativa usuarios", description = "Desativa usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PutMapping("/{id}")
    public void desativar(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        userService.desativarUsuario(id);
    }

}
