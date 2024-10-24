package com.github.andrepenteado.sso.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    private Long cpf;

    private Boolean enabled;

    @Column(name = "fk_upload")
    private UUID foto;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities",
               joinColumns = { @JoinColumn(name = "username") },
               inverseJoinColumns = { @JoinColumn(name = "authority") })
    private List<PerfilSistema> perfis;
}
