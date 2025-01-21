package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.assembler.AutuacaoAssembler;
import com.algaworks.algatransito.api.model.AutuacaoModel;
import com.algaworks.algatransito.api.model.VeiculoModel;
import com.algaworks.algatransito.api.model.input.AutuacaoInput;
import com.algaworks.algatransito.domain.model.Autuacao;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.AutuacaoRepository;
import com.algaworks.algatransito.domain.service.RegistroAutuacaoService;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{idVeiculo}/autuacoes")
public class AutuacaoController {

    private final AutuacaoAssembler autuacaoAssembler;

    private final RegistroAutuacaoService registroAutuacaoService;

    private final RegistroVeiculoService registroVeiculoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoModel cadastrar(@RequestBody AutuacaoInput autuacaoInput,
                                   @PathVariable Long idVeiculo) {
        var autuacao = autuacaoAssembler.toEntity(autuacaoInput);
        var autuacaoRegistrada = registroAutuacaoService.registrar(idVeiculo, autuacao);
        return autuacaoAssembler.toModel(autuacaoRegistrada);

    }

    @GetMapping
    public List<AutuacaoModel> listar(@PathVariable Long idVeiculo) {

        Veiculo veiculo = registroVeiculoService.buscar(idVeiculo);
        return autuacaoAssembler.toCollectiontoModel(veiculo.getAutuacoes());

    }

}
