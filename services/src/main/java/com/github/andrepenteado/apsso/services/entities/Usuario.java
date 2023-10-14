package com.github.andrepenteado.apsso.services.entities;

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

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataUltimaModificacao;

    @NotNull(message = "Nome do usuário é um campo obrigatório")
    private String nome;

    private Boolean enabled;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities",
               joinColumns = { @JoinColumn(name = "username") },
               inverseJoinColumns = { @JoinColumn(name = "authority") })
    private List<PerfilSistema> perfis;
}
