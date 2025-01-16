package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {


    private final ProprietarioRepository proprietarioRepository;

    public Proprietario buscar (Long proprietarioId) {
        return  proprietarioRepository.findById(proprietarioId)
                .orElseThrow(() -> new NegocioException("Proprietario não encontrado "));

    }

    @Transactional
    public Proprietario salvar(Proprietario proprietario) {
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario))
                .isPresent(); //Se isPresent() for true, significa que o e-mail já está em uso por outro proprietário.
        if (emailEmUso) {
            throw new NegocioException("Já existe um proprietario cadastrado com esse email ");
        }
        return  proprietarioRepository.save(proprietario);

        /*
        A lógica é:
            Se p for igual a proprietario, a expressão p.equals(proprietario) retorna true. O ! inverte isso, tornando o filtro false, ou seja, o proprietário será removido do fluxo.
            Se p for diferente de proprietario, a expressão p.equals(proprietario) retorna false, e o ! inverte para true. Isso significa que o filtro mantém esse proprietário no fluxo.


         */
    }

    public void excluir(Long proprietarioId) {
        proprietarioRepository.deleteById(proprietarioId);
    }
}
