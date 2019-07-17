---------------------------------------------------------

INSERT INTO Usuario (Login, Senha, Nome, Expiracao) VALUES ('admin', '$2a$10$YhKo.xR5mshUQqu4NOS/XuWQKKbEVAokBjHRhAAQI25dd3evoiGIi', 'Administrador', NULL);
INSERT INTO Perfil_Usuario (Id_Usuario, Perfil) VALUES (LASTVAL(), 'Administrador');

INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities,
 access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('com.gitlab.andrepenteado.apcontrole', '$2a$10$gGtZjuCrnFNK2L1f5Yj/BOBrXwsOYUSXZGn5Toz.2PKygvnID7Rxu', 'user_info', 'authorization_code', 'http://localhost:30001/ap-controle/login', null, 36000, 36000, null, true);

INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities,
 access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('com.gitlab.andrepenteado.apclinic', '$2a$10$yDpuM/dxG8IePXRBcBq2k.kN7bvzrF7c8H7E5U7c49U4blNe0I/uq', 'user_info', 'authorization_code', 'http://localhost:30002/ap-clinic/login', null, 36000, 36000, null, true);

---------------------------------------------------------