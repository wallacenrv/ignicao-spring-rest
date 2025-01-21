package com.algaworks.algatransito.domain.model;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    // @JoinColumn(name= "proprietario_id")
    private Proprietario proprietario;

    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")
    private String placa;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)// possibilita configurar o que queremos armazenar na coluna status
    private StatusVeiculo status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataCadastro;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    // mesmo o consumidor da API passando a propriedade. Náo sera regidtrada
    private OffsetDateTime dataApreensao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL) // vincula com o ManyToOne, esse cascade, qualquer mudanca que fizermos sera sincronizado , cascateado no banco de dados
    private List<Autuacao> autuacoes = new ArrayList<>();


    public Autuacao adicionarAutuacao(Autuacao autuacao) {

        autuacao.setDataOcorrencia(OffsetDateTime.now());
        autuacao.setVeiculo(this);
        getAutuacoes().add(autuacao); // jakarta ja fez um insert na tabela de autuacao

        return autuacao;

    }

    public void apreender() {
        if (estaApreendido()){
            throw new NegocioException("Veiculo já se esta apreendido");
        }
        setStatus(StatusVeiculo.APREENDIDO);
        setDataApreensao(OffsetDateTime.now());
    }

    public boolean estaApreendido() {
        return StatusVeiculo.APREENDIDO.equals(getStatus());
    }


    public void removerApreensao() {
        if(naoEstaApreendido()) {
            throw  new NegocioException("Veiculo nao esta apreendido");

        }
        setStatus(StatusVeiculo.REGULAR);
        setDataApreensao(null); // limpa
    }

    private boolean naoEstaApreendido() {
        return !estaApreendido();
    }
}
