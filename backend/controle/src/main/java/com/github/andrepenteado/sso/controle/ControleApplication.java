package com.github.andrepenteado.sso.controle;

import br.unesp.fc.andrepenteado.core.web.config.CorsConfig;
import br.unesp.fc.andrepenteado.core.web.config.SecurityConfig;
import br.unesp.fc.andrepenteado.core.web.resources.AuthResource;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOAuth2Service;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOidcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.github.andrepenteado.sso"
		},
		scanBasePackageClasses = {
				SecurityConfig.class,
				AuthResource.class,
				UserLoginOAuth2Service.class,
				UserLoginOidcService.class,
				CorsConfig.class
		}
)
@EntityScan(
		basePackages = {
				"com.github.andrepenteado.sso"
		}
)
@EnableJpaRepositories(
		basePackages = {
				"com.github.andrepenteado.sso"
		}
)
public class ControleApplication {

	public static final String PERFIL_ARQUITETO = "ROLE_com.github.andrepenteado.sso.controle_ARQUITETO";

    public static void main(String[] args) {
		SpringApplication.run(ControleApplication.class, args);
	}

}

