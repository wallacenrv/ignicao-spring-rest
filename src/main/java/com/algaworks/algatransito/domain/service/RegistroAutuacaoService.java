package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.model.Autuacao;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.AutuacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class RegistroAutuacaoService {

    private final AutuacaoRepository autuacaoRepository;

    private final RegistroVeiculoService registroVeiculoService;
    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao novaAutuacao) {

       Veiculo veiculo =  registroVeiculoService.buscar(veiculoId);

    veiculo.adicionarAutuacao(novaAutuacao);
    return novaAutuacao;

    }

}
