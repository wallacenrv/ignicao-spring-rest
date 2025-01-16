package com.algaworks.algatransito.api.controller.exceptionhandler;

import com.algaworks.algatransito.domain.exception.NegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//responsavel por capturar excecoes globais da apliacacao
public class ApiExceptionHandler {

    //Captura agora independente de qual controller vier
    @ExceptionHandler(NegocioException.class) // metodo que captura excecoes
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage()); // indica que o problema é do consumidor
    }
}
