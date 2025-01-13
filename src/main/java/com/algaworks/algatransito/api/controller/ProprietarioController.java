package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

/*
 É o mais recomendado, inclusive para fazer testes. é uma boa pratica :

   private final  ProprietarioRepository proprietarioRepository;

   public ProprietarioController(ProprietarioRepository proprietarioRepository){
        this.proprietarioRepository = proprietarioRepository;
    }

 */

    private final ProprietarioRepository proprietarioRepository;

    @GetMapping
    public List<Proprietario> listar() {

        var pessoa = proprietarioRepository.findByNomeContaining("Mar");
        pessoa.forEach(p -> System.out.println(p.getNome()));

        return proprietarioRepository.findAll();

    }

    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId) {

       // Optional<Proprietario> proprietario = proprietarioRepository.findById(proprietarioId);

        return proprietarioRepository.findById(proprietarioId)
                .map(proprietario -> ResponseEntity.ok(proprietario))
                .orElse(ResponseEntity.notFound().build());


   /* Outra forma de fazer :

        if (proprietario.isPresent()){
           return ResponseEntity.ok(proprietario.get());
        }
        return ResponseEntity.notFound().build();
    */
    }


}
