package com.github.andrepenteado.sso.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@ToString(of = "nome")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataUltimaAtualizacao;

    private String usuarioCadastro;

    private String usuarioUltimaAtualizacao;

    @NotNull(message = "Nome é um campo obrigatório")
    private String nome;

    @NotNull(message = "CPF é um campo obrigatório")
    private Long cpf;

    @NotNull(message = "Telefone é um campo obrigatório")
    private String telefone;

    private String email;

    private Long cep;

    private String logradouro;

    private String complemento;

    private Long numeroLogradouro;

    private String bairro;

    private String cidade;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "fk_unidade_administrativa")
    @NotNull(message = "Unidade administrativa é um campo obrigatório")
    private UnidadeAdministrativa unidadeAdministrativa;

    @ManyToOne
    @JoinColumn(name = "fk_cargo")
    @NotNull(message = "Cargo é um campo obrigatório")
    private Cargo cargo;

    @ManyToMany
    @JoinTable(
        name = "colaborador_unidade_administrativa",
        joinColumns = @JoinColumn(name = "fk_colaborador"),
        inverseJoinColumns = @JoinColumn(name = "fk_unidade_administrativa")
    )
    private List<UnidadeAdministrativa> unidadesAdministrativas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colaborador that = (Colaborador) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCpf(), that.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf());
    }

}
