package com.github.andrepenteado.sso.services.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "authority")
@ToString(of = "authority")
public class PerfilSistema implements Serializable {

    @Id
    @NotNull(message = "Perfil do sistema é um campo obrigatório")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "id_oauth2_registered_client", referencedColumnName = "id")
    @NotNull(message = "Sistema é um campo obrigatório")
    private Sistema sistema;

    @NotNull(message = "Descrição do perfil é um campo obrigatório")
    private String descricao;
}
