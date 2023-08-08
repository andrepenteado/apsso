INSERT INTO public.oauth2_registered_client (
  id, client_name, data_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
  authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  'APsso', 'Portal de single sign on de sistemas e usuários', now(), 'http://apsso-backend:30000', 'com.github.andrepenteado.apsso', '2023-06-08 20:48:04.032595', '{bcrypt}$2a$10$QBeQ7uA5lsHc3OEtB1Pi3.a0XxgR.kFdmvpkBv6qOM0mJGved5tpq',
  null, 'client_secret_basic',  'refresh_token,client_credentials,authorization_code', 'http://apsso-backend:30001/authorized,http://apsso-backend:30001/login/oauth2/code/apsso-oidc',
  '', 'openid', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (id, id_oauth2_registered_client, authority, descricao)
  VALUES (DEFAULT, 'APsso', 'ROLE_APsso_ARQUITETO', 'Arquiteto do Sistema');

INSERT INTO public.oauth2_registered_client (
  id, client_name, data_cadastro, url_entrada, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_authentication_methods,
  authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES (
  'AProove', 'Controle de prontuários e pacientes', now(), 'htp://aproove:30002', 'com.github.andrepenteado.aproove', '2023-06-08 20:48:04.103687', '{bcrypt}$2a$10$hzBM8oKJ1ivmfpIHH8q94uuBM5tXGsP84vJLKDgnf//zNjpO2A0K6',
  null, 'client_secret_basic', 'refresh_token,client_credentials,authorization_code', 'http://aproove:30002/login/oauth2/code/aproove-oidc,http://aproove:30002/authorized',
  '', 'openid', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
  '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}'
);
INSERT INTO perfil_sistema (id, id_oauth2_registered_client, authority, descricao)
  VALUES (DEFAULT, 'AProove', 'ROLE_AProove_FISIOTERAPEUTA', 'Fisioterapeuta');

INSERT INTO users (username, password, enabled, data_cadastro, nome) VALUES ('admin', '{bcrypt}$2a$10$DrggBKNTQujqeW2xPABOEuM1GgL.6VvdiZAP/hzChWxTj6TiWyLym', true, now(), 'Administrador');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_APsso_ARQUITETO');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_AProove_FISIOTERAPEUTA');