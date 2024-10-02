INSERT INTO public.oauth2_registered_client (
    id, client_name, data_cadastro, usuario_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
   'Controle', 'Módulo de cadastro de usuários e sistemas', now(), 'Arquiteto do Sistema', 'http://localhost:4200/controle', 'com.github.andrepenteado.sso.controle', '2023-01-01 00:00:00.000000',
   '{bcrypt}$2a$10$xG.KPzsgZddwndKL9AQWquv1FdQW232DWRcC2GSLtr34aUaEEUOOa', null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code',
   'http://localhost:8080/controle-backend/authorized,http://localhost:8080/controle-backend/login/oauth2/code/controle-oidc', 'http://localhost:8080/controle-backend/logout', 'openid',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, id_oauth2_registered_client, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.controle_ARQUITETO', 'Controle', 'Arquiteto do Sistema');

INSERT INTO public.oauth2_registered_client (
    id, client_name, data_cadastro, usuario_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
   'Portal', 'Portal de Sistemas', now(), 'Arquiteto do Sistema', 'http://localhost:4200/portal', 'com.github.andrepenteado.sso.portal', '2023-01-01 00:00:00.000000',
   '{bcrypt}$2a$10$iJMyj3siGWVf1OARieTkAeynUscGkapD9Giu/PjkEVnKod/0PF.dC', null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code',
   'http://localhost:8080/portal-backend/authorized,http://localhost:8080/portal-backend/login/oauth2/code/portal-oidc', 'http://localhost:8080/portal-backend/logout', 'openid',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, id_oauth2_registered_client, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.portal_USUARIO', 'Portal', 'Usuário de Sistemas');

INSERT INTO users (username, password, enabled, data_cadastro, usuario_cadastro, nome) VALUES ('arquiteto', '{bcrypt}$2a$10$kGE18ss4rjWDDbomByVRVejkbVt2rjXpTkW.hLWl1uOav.DTuO0Mu', true, now(), 'Arquiteto do Sistema', 'Arquiteto do Sistema');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.controle_ARQUITETO');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.portal_USUARIO');
