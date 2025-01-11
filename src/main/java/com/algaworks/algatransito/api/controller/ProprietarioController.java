package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProprietarioController {

    @GetMapping("/proprietarios")
    public List<Proprietario> listar () {
        var proprietario1 = new Proprietario();
       proprietario1.setId(1L);
       proprietario1.setNome("Joao");
       proprietario1.setTelefone("61 99862-5027");
       proprietario1.setEmail("wallacen_rv@hotmail.com");

       var proprietario2 = new Proprietario();
       proprietario2.setId(2L);
       proprietario2.setNome("Maria");
       proprietario2.setTelefone("61 3358-6739");
       proprietario2.setEmail("maria@gmail.com");

       return Arrays.asList(proprietario1, proprietario2);


    }
}
