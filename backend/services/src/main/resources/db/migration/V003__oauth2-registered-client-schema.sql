CREATE TABLE oauth2_registered_client (
  id varchar(100) NOT NULL,
  client_id varchar(100) NOT NULL,
  client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  client_secret varchar(200) DEFAULT NULL,
  client_secret_expires_at timestamp DEFAULT NULL,
  client_name varchar(200) NOT NULL,
  client_authentication_methods varchar(1000) NOT NULL,
  authorization_grant_types varchar(1000) NOT NULL,
  redirect_uris varchar(1000) DEFAULT NULL,
  post_logout_redirect_uris varchar(1000) DEFAULT NULL,
  scopes varchar(1000) NOT NULL,
  client_settings varchar(2000) NOT NULL,
  token_settings varchar(2000) NOT NULL,
  url_entrada text NULL,
  data_cadastro timestamp,
  data_ultima_atualizacao timestamp,
  usuario_cadastro varchar(50),
  usuario_ultima_atualizacao varchar(50),
  fk_upload UUID NULL,
  PRIMARY KEY (id)
);

CREATE TABLE perfil_sistema (
    authority text NOT NULL,
    id_oauth2_registered_client varchar(100) NOT NULL,
    descricao varchar(100) NOT NULL,
    PRIMARY KEY(authority),
    constraint fk_perfilsistema_oauth2registeredclient foreign key(id_oauth2_registered_client) references oauth2_registered_client(id)
);
