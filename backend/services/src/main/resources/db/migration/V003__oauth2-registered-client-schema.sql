CREATE TABLE empresa (
  id bigserial NOT NULL,
  data_cadastro timestamp,
  data_ultima_atualizacao timestamp,
  usuario_cadastro varchar(50),
  usuario_ultima_atualizacao varchar(50),
  razao_social text,
  cnpj bigint,
  telefone varchar(15),
  nome_fantasia text,
  email varchar(50),
  cep bigint,
  logradouro text,
  complemento text,
  numero_logradouro bigint,
  bairro text,
  cidade text,
  estado varchar(2),
  fk_empresa_matriz bigint,
  primary key (id),
  constraint fk_empresamatriz_empresa foreign key (fk_empresa_matriz) references empresa (id)
);

CREATE TABLE sistema (
  id bigserial not null,
  nome text not null,
  descricao text,
  data_cadastro timestamp,
  data_ultima_atualizacao timestamp,
  usuario_cadastro varchar(50),
  usuario_ultima_atualizacao varchar(50),
  fk_upload UUID NULL,
  fk_empresa bigint,
  primary key (id),
  constraint fk_sistema_empresa foreign key (fk_empresa) references empresa (id)
);

CREATE TABLE perfil_sistema (
  authority text NOT NULL,
  fk_sistema bigint NOT NULL,
  descricao varchar(100) NOT NULL,
  primary key (authority, fk_sistema),
  constraint fk_perfilsistema_sistema foreign key (fk_sistema) references sistema (id)
);

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
  fk_sistema bigint,
  primary key (id),
  constraint fk_oauth2registeredclient_sistema foreign key (fk_sistema) references sistema (id)
);

