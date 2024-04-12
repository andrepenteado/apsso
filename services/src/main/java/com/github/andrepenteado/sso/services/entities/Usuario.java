package com.github.andrepenteado.sso.services.entities;

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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataUltimaAtualizacao;

    private String usuarioCadastro;

    private String usuarioUltimaAtualizacao;

    @NotNull(message = "Nome do usuário é um campo obrigatório")
    private String nome;

    private Boolean enabled;

    private String fotoBase64;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities",
               joinColumns = { @JoinColumn(name = "username") },
               inverseJoinColumns = { @JoinColumn(name = "authority") })
    private List<PerfilSistema> perfis;
}
