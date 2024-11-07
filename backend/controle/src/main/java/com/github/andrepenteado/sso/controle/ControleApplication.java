package com.github.andrepenteado.sso.controle;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import br.unesp.fc.andrepenteado.core.upload.UploadRepository;
import br.unesp.fc.andrepenteado.core.upload.UploadResource;
import br.unesp.fc.andrepenteado.core.web.config.CorsConfig;
import br.unesp.fc.andrepenteado.core.web.config.SecurityConfig;
import br.unesp.fc.andrepenteado.core.web.resources.AuthResource;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOAuth2Service;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOidcService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@SpringBootApplication(
	scanBasePackages = {
		"com.github.andrepenteado.sso"
	},
	scanBasePackageClasses = {
		SecurityConfig.class,
		AuthResource.class,
		UserLoginOAuth2Service.class,
		UserLoginOidcService.class,
		CorsConfig.class,
		UploadResource.class
	}
)
@EntityScan(
	basePackages = {
		"com.github.andrepenteado.sso"
	},
	basePackageClasses = {
		Upload.class
	}
)
@EnableJpaRepositories(
	basePackages = {
		"com.github.andrepenteado.sso"
	},
	basePackageClasses = {
		UploadRepository.class
	}
)
@RequiredArgsConstructor
public class ControleApplication {

	public static final String PERFIL_ARQUITETO = "ROLE_com.github.andrepenteado.sso.controle_ARQUITETO";

    public static void main(String[] args) {
		SpringApplication.run(ControleApplication.class, args);
	}

	private final Environment environment;

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		ClientRegistration.Builder clientRegistration = ClientRegistration.withRegistrationId("com.github.andrepenteado.sso.controle");

		if (environment.matchesProfiles("dev")) {
			clientRegistration
				.clientId("com.github.andrepenteado.sso.controle")
				.clientSecret("controle-secret")
				.issuerUri("http://localhost:30000")
				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationUri("http://localhost:30000/oauth2/authorize")
				.tokenUri("http://localhost:30000/oauth2/token")
				.userInfoUri("http://localhost:30000/userinfo")
				.jwkSetUri("http://localhost:30000/oauth2/jwks")
				.scope("openid");
		}
		else {
			clientRegistration
				.clientId("com.github.andrepenteado.sso.controle")
				.clientSecret("controle-secret")
				.issuerUri("https://login.apcode.com.br")
				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationUri("https://login.apcode.com.br/oauth2/authorize")
				.tokenUri("https://login.apcode.com.br/oauth2/token")
				.userInfoUri("https://login.apcode.com.br/userinfo")
				.jwkSetUri("https://login.apcode.com.br/oauth2/jwks")
				.scope("openid");
		}

		return new InMemoryClientRegistrationRepository(clientRegistration.build());
	}

}

