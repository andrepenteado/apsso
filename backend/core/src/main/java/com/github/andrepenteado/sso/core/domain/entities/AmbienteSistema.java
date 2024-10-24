package com.github.andrepenteado.sso.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "oauth2_registered_client")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class AmbienteSistema {

    @Id
    @NotNull(message = "Sigla do ambiente do sistema é um campo obrigatório")
    private String id;

    @Column(name = "client_name")
    @NotNull(message = "Descrição do ambiente do sistema é um campo obrigatório")
    private String descricao;

    private String urlEntrada;

    @ManyToOne
    @JoinColumn(name = "fk_sistema")
    private Sistema sistema;

    private String clientId;

    private String clientSecret;

    private String redirectUris;

    private String postLogoutRedirectUris;

    private String clientAuthenticationMethods;

    private String authorizationGrantTypes;

    private String scopes;

    private String clientSettings;

    private String tokenSettings;

}
