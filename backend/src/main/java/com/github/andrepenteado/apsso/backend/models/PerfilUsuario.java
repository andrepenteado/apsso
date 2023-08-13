package com.github.andrepenteado.apsso.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "authorities")
@IdClass(PerfilUsuario.IdPerfilUsuario.class)
@Data
@EqualsAndHashCode(of = { "username", "authority" })
@ToString(of = { "username", "authority" })
public class PerfilUsuario {

    @Id
    @NotNull(message = "Nome de usuário é um campo obrigatório")
    private String username;

    @Id
    @NotNull(message = "Perfil do sistema é um campo obrigatório")
    private String authority;

    @Embeddable
    @Data
    public static class IdPerfilUsuario implements Serializable {

        private String username;

        private String authority;

    }
}
