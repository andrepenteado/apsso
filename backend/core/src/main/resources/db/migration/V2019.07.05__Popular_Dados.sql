INSERT INTO empresa (data_cadastro, usuario_cadastro, razao_social, nome_fantasia, cnpj) VALUES (now(), 'Arquiteto do Sistema', 'APcode Tecnologia', 'APcode', 111111111111100);

INSERT INTO sistema (data_cadastro, usuario_cadastro, identificador, nome, descricao, fk_empresa) VALUES (now(), 'Arquiteto do Sistema', 'com.github.andrepenteado.sso.controle', 'Módulo de Controle', 'Módulo de controle de permissões de usuários e sistemas', currval('empresa_id_seq'));
INSERT INTO public.oauth2_registered_client (
  id, client_name, url_acesso, uri_provider, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
  authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  gen_random_uuid(), 'Máquina Local', 'http://localhost:4200/controle/login', 'http://localhost:30000', 'LOCAL', currval('sistema_id_seq'), 'LOCAL-controle', '2023-01-01 00:00:00.000000',
  '{bcrypt}$2a$10$GgObVloKFqYDsfr2RGLtae0S0nWbypa6p73mZdMO3KLBvBO0Z/D7e', null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code',
  'http://localhost:8080/controle-backend/authorized,http://localhost:8080/controle-backend/login/oauth2/code/com.github.andrepenteado.sso.controle', 'http://localhost:8080/controle-backend/logout', 'openid',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO public.oauth2_registered_client (
    id, client_name, url_acesso, uri_provider, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  gen_random_uuid(), 'Servidor de Produção', 'https://sistemas.apcode.com.br/controle/login', 'https://login.apcode.com.br', 'PRODUCAO', currval('sistema_id_seq'), 'PRODUCAO-controle', '2023-01-01 00:00:00.000000',
  '{bcrypt}$2a$10$5LIt4KfAlzt78c/FvaKXGejNkBWbJIm7jnJizvZxhSwOlkOarpQ76', null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code',
  'https://sistemas.apcode.com.br/controle-backend/authorized,https://sistemas.apcode.com.br/controle-backend/login/oauth2/code/com.github.andrepenteado.sso.controle', 'https://sistemas.apcode.com.br/controle-backend/logout', 'openid',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, fk_sistema, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.controle_ARQUITETO', currval('sistema_id_seq'), 'Arquiteto do Sistema');

INSERT INTO sistema (data_cadastro, usuario_cadastro, identificador, nome, descricao, fk_empresa) VALUES (now(), 'Arquiteto do Sistema', 'com.github.andrepenteado.sso.portal', 'Portal de Sistemas', 'Portal de acesso a sistemas e serviços', currval('empresa_id_seq'));
INSERT INTO public.oauth2_registered_client (
  id, client_name, url_acesso, uri_provider, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
  authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  gen_random_uuid(), 'Máquina Local', 'http://localhost:4200/portal/login', 'http://localhost:30000','LOCAL', currval('sistema_id_seq'), 'LOCAL-portal', '2023-01-01 00:00:00.000000',
  '{bcrypt}$2a$10$v8qAdIL15OI0w6U3Z7eORObmm50Do1LG/JaDdsTEQzgazF3kmAM8y', null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code',
  'http://localhost:8080/portal-backend/authorized,http://localhost:8080/portal-backend/login/oauth2/code/com.github.andrepenteado.sso.portal', 'http://localhost:8080/portal-backend/logout', 'openid',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO public.oauth2_registered_client (
    id, client_name, url_acesso, uri_provider, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  gen_random_uuid(), 'Servidor de Produção', 'https://sistemas.apcode.com.br/portal/login', 'https://login.apcode.com.br','PRODUCAO', currval('sistema_id_seq'), 'PRODUCAO-portal', '2023-01-01 00:00:00.000000',
  '{bcrypt}$2a$10$xfxYJswuh3wHCKCyH9PfFOZprAyALhvAu3vpkPSZvuWxjFZqtha5G', null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code',
  'https://sistemas.apcode.com.br/portal-backend/authorized,https://sistemas.apcode.com.br/portal-backend/login/oauth2/code/com.github.andrepenteado.sso.portal', 'https://sistemas.apcode.com.br/portal-backend/logout', 'openid',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, fk_sistema, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.portal_USUARIO', currval('sistema_id_seq'), 'Usuário de Sistemas');

INSERT INTO sistema (data_cadastro, usuario_cadastro, identificador, nome, descricao, fk_empresa) VALUES (now(), 'Arquiteto do Sistema', 'com.github.andrepenteado.sso.equipe', 'Gestão de Equipes', 'Módulo de gestão de colaboradores, equipes e seus vínculos', currval('empresa_id_seq'));
INSERT INTO public.oauth2_registered_client (
  id, client_name, url_acesso, uri_provider, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
  authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  gen_random_uuid(), 'Máquina Local', 'http://localhost:4200/equipe/login', 'http://localhost:30000','LOCAL', currval('sistema_id_seq'), 'LOCAL-equipe', '2023-01-01 00:00:00.000000',
  '{bcrypt}$2a$10$ouqKfDtW4LClapVVHX2wvOc07ru6gCXy2MiN.eZKi1eIVFfFvDL2W', null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code',
  'http://localhost:8080/equipe-backend/authorized,http://localhost:8080/equipe-backend/login/oauth2/code/com.github.andrepenteado.sso.equipe', 'http://localhost:8080/equipe-backend/logout', 'openid',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO public.oauth2_registered_client (
    id, client_name, url_acesso, uri_provider, tipo, fk_sistema, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  gen_random_uuid(), 'Servidor de Produção', 'https://sistemas.apcode.com.br/equipe/login', 'https://login.apcode.com.br','PRODUCAO', currval('sistema_id_seq'), 'PRODUCAO-equipe', '2023-01-01 00:00:00.000000',
  '{bcrypt}$2a$10$hbJqfQAp.8Q5bpOG/OgWp.xSrxfuYiYpYOtBpqtP0yCUp2Hm8G5JS', null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code',
  'https://sistemas.apcode.com.br/equipe-backend/authorized,https://sistemas.apcode.com.br/equipe-backend/login/oauth2/code/com.github.andrepenteado.sso.equipe', 'https://sistemas.apcode.com.br/equipe-backend/logout', 'openid',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, fk_sistema, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.equipe_GESTOR', currval('sistema_id_seq'), 'Gestor de Equipes');

INSERT INTO sistema (data_cadastro, usuario_cadastro, identificador, nome, descricao, fk_empresa) VALUES (now(), 'Arquiteto do Sistema', 'com.github.andrepenteado.sso.api', 'API de Consulta', 'API Rest de consultaa usuários e sistemas', currval('empresa_id_seq'));
INSERT INTO perfil_sistema (authority, fk_sistema, descricao)
VALUES ('ROLE_com.github.andrepenteado.sso.api_CONSULTAR_API', currval('sistema_id_seq'), 'Permissão de Consulta');

INSERT INTO users (username, password, enabled, data_cadastro, usuario_cadastro, nome, cpf, email) VALUES ('arquiteto', '{bcrypt}$2a$10$kGE18ss4rjWDDbomByVRVejkbVt2rjXpTkW.hLWl1uOav.DTuO0Mu', true, now(), 'Arquiteto do Sistema', 'Arquiteto do Sistema', 11111111100, 'arquiteto@apcode.com.br');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.controle_ARQUITETO');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.portal_USUARIO');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.equipe_GESTOR');
INSERT INTO authorities (username, authority) VALUES ('arquiteto', 'ROLE_com.github.andrepenteado.sso.api_CONSULTAR_API');
