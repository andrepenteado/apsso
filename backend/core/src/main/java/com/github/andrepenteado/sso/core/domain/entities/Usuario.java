package com.github.andrepenteado.sso.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@ToString(of = "nome")
public class Usuario {

    @Id
    private String username;

    @NotNull(message = "Senha é um campo obrigatório")
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataUltimaAtualizacao;

    private String usuarioCadastro;

    private String usuarioUltimaAtualizacao;

    @NotNull(message = "Nome do usuário é um campo obrigatório")
    private String nome;

    @NotNull(message = "CPF do usuário é um campo obrigatório")
    private Long cpf;

    @NotNull(message = "É obrigatório informar se o login do usuário está ativo ou inativo")
    private Boolean enabled;

    @Column(name = "fk_upload")
    private UUID foto;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    private List<Usuario> usuarios;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities",
               joinColumns = { @JoinColumn(name = "username") },
               inverseJoinColumns = { @JoinColumn(name = "authority") })
    private List<PerfilSistema> perfis;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;
        return username.equals(usuario.username) && cpf.equals(usuario.cpf);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + cpf.hashCode();
        return result;
    }

}
