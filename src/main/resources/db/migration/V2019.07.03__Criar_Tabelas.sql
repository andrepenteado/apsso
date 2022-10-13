--------------------------------------------------------

DROP TABLE IF EXISTS Usuario CASCADE;
CREATE TABLE IF NOT EXISTS Usuario
(
  Id        BIGSERIAL,
  Login     VARCHAR(100)    NOT NULL,
  Senha     VARCHAR(100)        NULL,
  Nome      VARCHAR(300)    NOT NULL,
  Expiracao DATE                NULL,
  CONSTRAINT PK_Usuario         PRIMARY KEY (Id),
  CONSTRAINT UN_Usuario_Login   UNIQUE (Login)
);

--------------------------------------------------------

DROP TABLE IF EXISTS Perfil_Usuario CASCADE;
CREATE TABLE IF NOT EXISTS Perfil_Usuario
(
  Id         BIGSERIAL,
  Id_Usuario BIGINT          NOT NULL,
  Perfil     VARCHAR(100)    NOT NULL,
  CONSTRAINT PK_Perfil_Usuario                PRIMARY KEY (Id),
  CONSTRAINT UN_PerfilUsuario_PerfilIdUsuario UNIQUE (Perfil, Id_Usuario),
  CONSTRAINT FK_PerfilUsuario_Usuario         FOREIGN KEY (Id_Usuario) REFERENCES Usuario (Id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

---------------------------------------------------------
