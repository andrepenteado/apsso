package com.github.andrepenteado.sso.services.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "oauth2_registered_client")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Sistema {

    @Id
    @NotNull(message = "Sigla do sistema é um campo obrigatório")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataUltimaAtualizacao;

    private String usuarioCadastro;

    private String usuarioUltimaAtualizacao;

    @Column(name = "client_name")
    @NotNull(message = "Descrição do sistema é um campo obrigatório")
    private String descricao;

    private String urlEntrada;

    private String iconeBase64;

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
