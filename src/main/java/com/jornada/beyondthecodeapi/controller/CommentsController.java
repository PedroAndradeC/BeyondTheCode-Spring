package com.jornada.beyondthecodeapi.controller;

import com.jornada.beyondthecodeapi.dto.CommentsDTO;
import com.jornada.beyondthecodeapi.exception.RegraDeNegocioException;
import com.jornada.beyondthecodeapi.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentsController {

    private final CommentsService commentsService;

    @Operation(summary = "Insere novo comentário", description = "Este processo realiza a inserção de novo comentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PostMapping
    public CommentsDTO inserirPost(@RequestBody @Valid CommentsDTO comments) throws RegraDeNegocioException {
        log.info("Post foi inserido");
        return commentsService.salvarComments(comments);
    }

    @Operation(summary = "Retorna todos os comentários", description = "Este processo retorna todos os comentários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @GetMapping
    public List<CommentsDTO> retornarTodosOsPosts(){
        return commentsService.listar();
    }

    @Operation(summary = "Atualiza comentário", description = "Atualiza um comentário na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @PutMapping
    public CommentsDTO atualizarPost(@RequestBody @Valid CommentsDTO comments) throws RegraDeNegocioException {
        return commentsService.atualizarComments(comments);
    }

    @Operation(summary = "Deleta Comentário", description = "Deleta Comentários na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Deu certo!"),
            @ApiResponse(responseCode = "400",description = "Erro na validação de dados"),
            @ApiResponse(responseCode = "500",description = "Erro do servidor")
    })
    @DeleteMapping("/{idComments}")
    public void remover(@PathVariable("idComments") Integer id) {
        commentsService.remover(id);
    }
}

