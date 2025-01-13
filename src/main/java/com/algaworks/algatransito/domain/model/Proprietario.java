package com.algaworks.algatransito.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor // Construtor sem argumentos (necessário para JPA)
@AllArgsConstructor // Construtor com todos os argumentos (necessário para Lombok Builder)
@Table(name = "proprietario")
@Entity
public class Proprietario {



    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;



    // Construtor padrão (necessário para o JPA)

    // compara objetos
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Proprietario that = (Proprietario) o;
//        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(telefone, that.telefone);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, nome, email, telefone);
//    }
}
