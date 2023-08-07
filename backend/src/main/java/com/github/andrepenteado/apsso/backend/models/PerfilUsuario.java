package com.github.andrepenteado.apsso.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "authorities")
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

}
