package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProprietarioController {


    // um gerenciador de entidades
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/proprietarios")
    public List<Proprietario> listar () {

       TypedQuery<Proprietario> query = entityManager.createQuery("from Proprietario", Proprietario.class);
return query.getResultList();

//        Proprietario proprietario1 = Proprietario.builder()
//                .id(1L)
//                .nome("João")
//                .telefone("61 99862-5027")
//                .email("wallacen_rv@hotmail.com")
//                .build();
//
//        Proprietario proprietario2 = Proprietario.builder()
//                .id(2L)
//                .nome("Maria")
//                .telefone("61 3358-6739")
//                .email("maria@gmail.com")
//                .build();
//
//        Proprietario proprietario3 = Proprietario.builder()
//                .id(3L)
//                .nome("Pedro")
//                .email("pedro@gmail.com")
//                .telefone("61 98097548").build();
//
//       return Arrays.asList(proprietario1, proprietario2,proprietario3);


    }
}
