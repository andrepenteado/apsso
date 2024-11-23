package com.github.andrepenteado.sso.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Entity
@Data
@ToString(of = "nome")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome é um campo obrigatório")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    @NotNull(message = "Empresa é um campo obrigatório")
    private Empresa empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return Objects.equals(getId(), cargo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
