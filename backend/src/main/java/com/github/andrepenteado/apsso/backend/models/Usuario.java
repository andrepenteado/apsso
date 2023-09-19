package com.github.andrepenteado.apsso.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(of = "nome")
@ToString(of = "nome")
public class Usuario {

    @Id
    private String username;

    @NotNull(message = "Senha é um campo obrigatório")
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataUltimaModificacao;

    @NotNull(message = "Nome do usuário é um campo obrigatório")
    private String nome;

    private Boolean enabled;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "authorities",
               joinColumns = { @JoinColumn(name = "username") },
               inverseJoinColumns = { @JoinColumn(name = "authority") })
    private List<PerfilSistema> perfis;
}
