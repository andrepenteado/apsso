package com.github.andrepenteado.apsso.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class PerfilSistema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_oauth2_registered_client", referencedColumnName = "id")
    @NotNull(message = "Sistema é um campo obrigatório")
    private Sistema sistema;

    @NotNull(message = "Perfil do sistema é um campo obrigatório")
    private String authority;

    @NotNull(message = "Descrição do perfil é um campo obrigatório")
    private String descricao;
}
