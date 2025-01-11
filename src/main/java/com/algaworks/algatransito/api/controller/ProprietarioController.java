package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import lombok.Builder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Builder
@RestController
public class ProprietarioController {

    @GetMapping("/proprietarios")
    public List<Proprietario> listar () {

        Proprietario proprietario1 = Proprietario.builder()
                .id(1L)
                .nome("João")
                .telefone("61 99862-5027")
                .email("wallacen_rv@hotmail.com")
                .build();

        Proprietario proprietario2 = Proprietario.builder()
                .id(2L)
                .nome("Maria")
                .telefone("61 3358-6739")
                .email("maria@gmail.com")
                .build();

        Proprietario proprietario3 = Proprietario.builder()
                .id(3L)
                .nome("Pedro")
                .email("pedro@gmail.com")
                .telefone("61 98097548").build();

       return Arrays.asList(proprietario1, proprietario2,proprietario3);


    }
}
