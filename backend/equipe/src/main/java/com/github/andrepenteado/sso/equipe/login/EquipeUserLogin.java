package com.github.andrepenteado.sso.equipe.login;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.List;

@Getter @Setter
public class EquipeUserLogin extends UserLogin {

    private List<Empresa> empresas;

    private String fotoBase64;

    public EquipeUserLogin(DefaultOidcUser userLogin) {
        super(userLogin);
    }

}
