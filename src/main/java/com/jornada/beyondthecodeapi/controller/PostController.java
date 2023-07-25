package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.PostDTO;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "Insere novo Post", description = "Este processo realiza a inserção de novo Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping
    public PostDTO inserirPost(@RequestBody @Valid PostDTO post) throws RegraDeNegocioException{
        return postService.salvarPost(post);
    }

    @Operation(summary = "Retorna todos os Posts", description = "Este processo retorna todos os Posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping
    public List<PostDTO> retornarTodosOsPosts(){
        return postService.listar();
    }

    @Operation(summary = "Atualiza Post", description = "Atualiza um Post na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PutMapping
    public boolean atualizarPost(@RequestBody @Valid PostDTO post) throws RegraDeNegocioException {
        return postService.editar(post);
    }

    @Operation(summary = "Deleta Posts", description = "Deleta Posts na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @DeleteMapping("/{idPost}")
    public boolean remover(@PathVariable("idPost") Integer idPost, Integer idUser) throws RegraDeNegocioException {
        return postService.excluir(idPost,idUser);
    }
}
