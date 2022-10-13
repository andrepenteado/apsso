---------------------------------------------------------

INSERT INTO Usuario (Login, Senha, Nome, Expiracao) VALUES ('admin', '$2a$10$YhKo.xR5mshUQqu4NOS/XuWQKKbEVAokBjHRhAAQI25dd3evoiGIi', 'Administrador', NULL);
INSERT INTO Perfil_Usuario (Id_Usuario, Perfil) VALUES (LASTVAL(), 'Administrador');

---------------------------------------------------------
