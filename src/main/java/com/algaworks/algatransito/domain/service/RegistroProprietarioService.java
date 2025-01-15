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
    @Transactional
    public Proprietario salvar(Proprietario proprietario) {
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario)) //Verifica se o proprietário encontrado não é o mesmo que está sendo salvo.  Permite verificar a duplicidade de e-mail sem impedir atualizações legítimas.
                .isPresent();
        if (emailEmUso) {
            throw new NegocioException("Já existe um proprietario cadastrado com esse email ");
        }
        return  proprietarioRepository.save(proprietario);

    }

    public void excluir(Long proprietarioId) {
        proprietarioRepository.deleteById(proprietarioId);
    }
}
