package com.algaworks.algatransito.domain.model;

import com.algaworks.algatransito.domain.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne
    @Valid // validacao em cascata
    @ConvertGroup(from = Default.class, to = ValidationGroups.ProprietarioId.class)
   // @JoinColumn(name= "proprietario_id")
    private Proprietario proprietario;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank //xxx0000  xxx0x00
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")//EXPRESSAO REGULAR
    private String placa;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)// possibilita configurar o que queremos armazenar na coluna status
    private StatusVeiculo status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // mesmo o consumidor da API passando a propriedade. Náo sera regidtrada
    private LocalDateTime dataApreensao;

}
