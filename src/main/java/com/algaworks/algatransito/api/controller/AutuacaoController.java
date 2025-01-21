package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.assembler.AutuacaoAssembler;
import com.algaworks.algatransito.api.model.AutuacaoModel;
import com.algaworks.algatransito.api.model.VeiculoModel;
import com.algaworks.algatransito.api.model.input.AutuacaoInput;
import com.algaworks.algatransito.domain.model.Autuacao;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.service.RegistroAutuacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{idVeiculo}/autuacoes")
public class AutuacaoController {

    private final AutuacaoAssembler autuacaoAssembler;

    private final RegistroAutuacaoService registroAutuacaoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoModel cadastrar(@RequestBody AutuacaoInput autuacaoInput,
                                   @PathVariable Long idVeiculo) {
        var autuacao = autuacaoAssembler.toEntity(autuacaoInput);
        var autuacaoRegistrada = registroAutuacaoService.registrar(idVeiculo, autuacao);
        return autuacaoAssembler.toModel(autuacaoRegistrada);

    }
}
