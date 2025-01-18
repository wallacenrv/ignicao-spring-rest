package com.algaworks.algatransito.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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

}
