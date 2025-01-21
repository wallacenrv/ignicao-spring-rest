package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.model.StatusVeiculo;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {


    private final VeiculoRepository veiculoRepository;

    @Autowired
    private RegistroProprietarioService registroProprietarioService;


    public Veiculo buscar(Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException("Veiculo não encontrado"));

    }
    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo) {

        if (novoVeiculo.getId() != null) {
            throw new NegocioException("Veículo a ser cadastrado nao deve possuir um codigo");
        }

        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                .filter(veiculo -> !veiculo.equals(novoVeiculo))
                .isPresent();
        if (placaEmUso) {
            throw new NegocioException("Já existe um veiculo cadastrado com essa placa");
        }

        Proprietario proprietarioBuscado = registroProprietarioService.buscar(novoVeiculo.getProprietario().getId());

        novoVeiculo.setProprietario(proprietarioBuscado);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(OffsetDateTime.now());

        return veiculoRepository.save(novoVeiculo);

        //2023-05-18T15:00:00\
        //2023-05-18T15:00:00-003:00 OFFSET MESMA COISA 2023-05-18T15:00:00Z
    }
}
