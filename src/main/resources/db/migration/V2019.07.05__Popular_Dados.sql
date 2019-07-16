---------------------------------------------------------

INSERT INTO Usuario (Login, Senha, Nome, Expiracao) VALUES ('admin', '$2a$10$YhKo.xR5mshUQqu4NOS/XuWQKKbEVAokBjHRhAAQI25dd3evoiGIi', 'Administrador', NULL);
INSERT INTO Perfil_Usuario (Id_Usuario, Perfil) VALUES (LASTVAL(), 'Administrador');

INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities,
 access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('com.gitlab.andrepenteado.apclinic', '$2a$10$Pm7bLyuhA4Fhp4h0wmOKgebm8X892LGSJMdCGoI3oP8t/2hSMFvSm', 'user_info', 'authorization_code', 'http://localhost:8080/apclinic/login', null, 36000, 36000, null, true);

INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities,
 access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('com.gitlab.andrepenteado.controle', '$2a$10$qm2SDJrtELnkXfo2fKCGkOjuhZ5pV6G07B3b84CDs57W7cD7Kb/ga', 'user_info', 'authorization_code', 'http://localhost:30001/apcontrole/login', null, 36000, 36000, null, true);

---------------------------------------------------------