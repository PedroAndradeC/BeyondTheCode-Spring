package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.UserDTO;
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
@Controller
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

    @Operation(summary = "Realiza o login do Usuário", description = "Este processo realiza o login de Usuário já existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.loginUser(userDTO), HttpStatus.OK);
    }
//    public UserDTO loginUsuario(@RequestBody @Valid UserDTO user) throws RegraDeNegocioException {
//        // Verifica se os campos (email e senha) foram preenchidos
//        if (user.getEmail() == null || user.getSenha() == null) {
//            throw new RegraDeNegocioException("E-mail e senha são obrigatórios para o login.");
//        }
//
//        // autenticação q verifica se o usuário existe e a senha está correta
//        UserDTO autenticarUser = userService.autenticar(user.getEmail(), user.getSenha());
//
//        if (autenticarUser == null) {
//            throw new RegraDeNegocioException("Usuário não encontrado ou senha incorreta.");
//        }
//
//        return autenticarUser;
//    }

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
}
