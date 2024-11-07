INSERT INTO empresa (data_cadastro, usuario_cadastro, razao_social, cnpj) VALUES (now(), 'Arquiteto do Sistema', 'APcode Tecnologia', 111111111111100);

INSERT INTO sistema (data_cadastro, usuario_cadastro, identificador, nome, descricao, fk_empresa) VALUES (now(), 'Arquiteto do Sistema', 'com.github.andrepenteado.sso.controle', 'Módulo de Controle', 'Módulo de controle de permissões de usuários e sistemas', currval('empresa_id_seq'));
INSERT INTO public.oauth2_registered_client (
    id, client_name, url_acesso, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
   'com.github.andrepenteado.sso.controle_LOCAL', 'Máquina Local', 'http://localhost:4200/controle', 'LOCAL', currval('sistema_id_seq'), 'com.github.andrepenteado.sso.controle', '2023-01-01 00:00:00.000000',
   '{bcrypt}$2a$10$xG.KPzsgZddwndKL9AQWquv1FdQW232DWRcC2GSLtr34aUaEEUOOa', null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code',
   'http://localhost:8080/controle-backend/authorized,http://localhost:8080/controle-backend/login/oauth2/code/com.github.andrepenteado.sso.controle', 'http://localhost:8080/controle-backend/logout', 'openid',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, fk_sistema, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.controle_ARQUITETO', currval('sistema_id_seq'), 'Arquiteto do Sistema');

INSERT INTO sistema (data_cadastro, usuario_cadastro, identificador, nome, descricao, fk_empresa) VALUES (now(), 'Arquiteto do Sistema', 'com.github.andrepenteado.sso.portal', 'Portal de Sistemas', 'Portal de acesso a sistemas e serviços', currval('empresa_id_seq'));
INSERT INTO public.oauth2_registered_client (
    id, client_name, url_acesso, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
   'com.github.andrepenteado.sso.portal_LOCAL', 'Máquina Local', 'http://localhost:4200/portal', 'LOCAL', currval('sistema_id_seq'), 'com.github.andrepenteado.sso.portal', '2023-01-01 00:00:00.000000',
   '{bcrypt}$2a$10$iJMyj3siGWVf1OARieTkAeynUscGkapD9Giu/PjkEVnKod/0PF.dC', null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code',
   'http://localhost:8080/portal-backend/authorized,http://localhost:8080/portal-backend/login/oauth2/code/com.github.andrepenteado.sso.portal', 'http://localhost:8080/portal-backend/logout', 'openid',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, fk_sistema, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.portal_USUARIO', currval('sistema_id_seq'), 'Usuário de Sistemas');

INSERT INTO users (username, password, enabled, data_cadastro, usuario_cadastro, nome, cpf) VALUES ('arquiteto', '{bcrypt}$2a$10$kGE18ss4rjWDDbomByVRVejkbVt2rjXpTkW.hLWl1uOav.DTuO0Mu', true, now(), 'Arquiteto do Sistema', 'Arquiteto do Sistema', 11111111100);
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.controle_ARQUITETO');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.portal_USUARIO');
