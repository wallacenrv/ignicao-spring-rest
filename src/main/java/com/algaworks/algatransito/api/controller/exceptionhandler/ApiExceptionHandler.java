package com.algaworks.algatransito.api.controller.exceptionhandler;

import com.algaworks.algatransito.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice//responsavel por capturar excecoes globais da apliacacao
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    //Usando a RFC 7807 - quando ResponseEntityExceptionHandler

    private final MessageSource messageSource;

    //Captura agora independente de qual controller vier
    @ExceptionHandler(NegocioException.class) // metodo que captura excecoes
    public ProblemDetail handleNegocio(NegocioException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algatransito.com/erros/regra-de-negocio"));

        return problemDetail;
    }


    @ExceptionHandler(DataIntegrityViolationException.class) // metodo que captura excecoes
    public ProblemDetail handlDataIntegrityViolationException(DataIntegrityViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Recurso esta em Uso");
        problemDetail.setType(URI.create("https://algatransito.com/erros/recurso-em-uso"));

        return problemDetail;
    }

    /*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

       1-  Peguei o metodo para sobescrever :baseado
        2025-01-16T11:39:36.574-03:00  WARN 26877 --- [nio-8080-exec-1] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public com.algaworks.algatransito.domain.model.Proprietario com.algaworks.algatransito.api.controller.ProprietarioController.adicionar(com.algaworks.algatransito.domain.model.Proprietario): [Field error in object 'proprietario' on field 'nome': rejected value []; codes [NotBlank.proprietario.nome,NotBlank.nome,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [proprietario.nome,nome]; arguments []; default message [nome]]; default message [não deve estar em branco]] ]
       2- Entrei pelo ResponseEntityExceptionHandler e peguei o metodo
       3- vamos instanciar o ProblemDetail, com ele nos podemos cusgtomizar os campos do retorno ao erro
     */
    @Override // trata validacao de campos
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estao invalidos");
        problemDetail.setType(URI.create("https://algatransito.com/erros/campos-invalidos"));//REPSEMTA UMA uri

        // Campos customizados no corpo - vamos saber quais propriedade deu problema
        var fields = ex.getBindingResult().getAllErrors() // lista de todas as propriedades que esta com problemas
                .stream()
                .collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(), //coletos todos os elementos desse stream e passo para um map (NOME DO CAMPO)
                                objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))); // esse messaSource resolve essa mensagem e busca no arquivo message.properties que esta na pasra resource
                     //   objectError -> objectError.getDefaultMessage())); //MENSAGEM DO CAMPO

        problemDetail.setProperty("fields", fields);

        // o pulo do gato é passar o problemDetail
        return this.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

}
