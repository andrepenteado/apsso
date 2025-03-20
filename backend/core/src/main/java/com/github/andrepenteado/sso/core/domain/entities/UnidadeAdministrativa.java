package com.github.andrepenteado.sso.core.domain.entities;

import com.github.andrepenteado.sso.core.domain.enums.TipoUnidadeAdministrativa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@ToString(of = "nome")
public class UnidadeAdministrativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome é um campo obrigatório")
    private String nome;

    @NotNull(message = "Tipo é um campo obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoUnidadeAdministrativa tipo;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    @NotNull(message = "Empresa é um campo obrigatório")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "fk_colaborador_responsavel")
    private Colaborador responsavel;

    @ManyToOne
    @JoinColumn(name = "fk_unidade_administrativa_superior")
    private UnidadeAdministrativa unidadeAdministrativaSuperior;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "colaborador_unidade_administrativa",
        joinColumns = { @JoinColumn(name = "fk_unidade_administrativa") },
        inverseJoinColumns = { @JoinColumn(name = "fk_colaborador") })
    private List<Colaborador> colaboradores;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadeAdministrativa that = (UnidadeAdministrativa) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}