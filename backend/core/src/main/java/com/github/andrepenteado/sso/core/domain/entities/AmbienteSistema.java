package com.github.andrepenteado.sso.core.domain.entities;

import com.github.andrepenteado.sso.core.domain.enums.TipoAmbiente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "oauth2_registered_client")
@Data
@ToString(of = "descricao")
public class AmbienteSistema {

    @Id
    private String id;

    @Column(name = "client_name")
    @NotNull(message = "Descrição do ambiente do sistema é um campo obrigatório")
    private String descricao;

    @NotNull(message = "URL do sistema é um campo obrigatório")
    private String urlAcesso;

    @NotNull(message = "Tipo do ambiente é um campo obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoAmbiente tipo;

    @ManyToOne
    @JoinColumn(name = "fk_sistema")
    @NotNull(message = "Sistema é um campo obrigatório")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmbienteSistema that = (AmbienteSistema) o;
        return id.equals(that.id) && sistema.equals(that.sistema);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sistema.hashCode();
        return result;
    }

}
