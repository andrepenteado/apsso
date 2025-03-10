package com.github.andrepenteado.sso.equipe.login;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.core.domain.entities.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter @Setter
public class EquipeUserLogin extends UserLogin {

    private Usuario usuario;

    private String fotoBase64;

    public EquipeUserLogin(DefaultOidcUser userLogin) {
        super(userLogin);
    }

    public EquipeUserLogin(DefaultOAuth2User userLogin) {
        super(userLogin);
    }
}
