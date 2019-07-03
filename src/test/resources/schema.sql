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

DROP TABLE IF EXISTS oauth_client_details CASCADE;
CREATE TABLE oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256)
);

---------------------------------------------------------