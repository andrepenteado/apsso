INSERT INTO public.oauth2_registered_client (
    id, client_name, data_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
   'Controle', 'Módulo de cadastro de usuários e sistemas', now(), 'https://controle.apcode.com.br', 'com.github.andrepenteado.apsso.controle', '2023-01-01 00:00:00.000000', '{bcrypt}$2a$10$xG.KPzsgZddwndKL9AQWquv1FdQW232DWRcC2GSLtr34aUaEEUOOa',
   null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code', 'https://controle.apcode.com.br/authorized,https://controle.apcode.com.br/login/oauth2/code/controle-oidc',
   'https://controle.apcode.com.br/logout', 'openid', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, id_oauth2_registered_client, descricao)
VALUES ('ROLE_Controle_ARQUITETO', 'Controle', 'Arquiteto do Sistema');

INSERT INTO public.oauth2_registered_client (
    id, client_name, data_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
   'Portal', 'Portal de Sistemas', now(), 'https://portal.apcode.com.br', 'com.github.andrepenteado.apsso.portal', '2023-01-01 00:00:00.000000', '{bcrypt}$2a$10$iJMyj3siGWVf1OARieTkAeynUscGkapD9Giu/PjkEVnKod/0PF.dC',
   null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code', 'https://portal.apcode.com.br/authorized,https://portal.apcode.com.br/login/oauth2/code/portal-oidc',
   'https://portal.apcode.com.br/logout', 'openid', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
   '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (authority, id_oauth2_registered_client, descricao)
VALUES ('ROLE_Portal_USUARIO', 'Portal', 'Usuário de Sistemas');

INSERT INTO public.oauth2_registered_client (
    id, client_name, data_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
    authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
           'AProove', 'Sistema de pacientes e prontuários', now(), 'https://aproove.apcode.com.br', 'com.github.andrepenteado.aproove', '2023-01-01 00:00:00.000000', '{bcrypt}$2a$10$iJMyj3siGWVf1OARieTkAeynUscGkapD9Giu/PjkEVnKod/0PF.dC',
           null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code', 'https://aproove.apcode.com.br/authorized,https://aproove.apcode.com.br/login/oauth2/code/aproove-oidc',
           'https://aproove.apcode.com.br/logout', 'openid', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
           '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
       );
INSERT INTO perfil_sistema (authority, id_oauth2_registered_client, descricao)
VALUES ('ROLE_AProove_FISIOTERAPEUTA', 'AProove', 'Fisioterapeuta');

INSERT INTO users (username, password, enabled, data_cadastro, nome) VALUES ('admin', '{bcrypt}$2a$10$DrggBKNTQujqeW2xPABOEuM1GgL.6VvdiZAP/hzChWxTj6TiWyLym', true, now(), 'Administrador');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_Controle-DEV_ARQUITETO');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_Portal-DEV_USUARIO');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_Controle_ARQUITETO');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_Portal_USUARIO');
