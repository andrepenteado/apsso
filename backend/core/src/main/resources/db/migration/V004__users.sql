create table users(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null,
    nome text not null,
    cpf bigint not null,
    data_cadastro timestamp,
    data_ultima_atualizacao timestamp,
    usuario_cadastro varchar(50),
    usuario_ultima_atualizacao varchar(50),
    fk_upload UUID NULL,
    constraint un_users_cpf unique (cpf)
);

create table authorities (
    username text not null,
    authority text not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index un_authorities_usernameauthority on authorities (username,authority);

create table groups (
    id bigserial primary key,
    group_name varchar(50) not null
);

create table group_authorities (
    group_id bigint not null,
    authority text not null,
    constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

create table group_members (
    id bigserial primary key,
    username varchar(50) not null,
    group_id bigint not null,
    constraint fk_group_members_group foreign key(group_id) references groups(id)
);

CREATE TABLE cargo (
  id bigserial not null,
  nome text not null,
  fk_empresa bigint not null,
  primary key (id),
  constraint fk_cargo_empresa foreign key (fk_empresa) references empresa (id)
);

CREATE TABLE unidade_administrativa (
  id bigserial not null,
  nome text not null,
  tipo varchar(50) not null,
  fk_empresa bigint not null,
  fk_colaborador_responsavel bigint null,
  fk_unidade_administrativa_superior bigint null,
  primary key (id),
  constraint fk_unidadeadministrativa_empresa foreign key (fk_empresa) references empresa (id),
  constraint fk_unidadeadministrativa_unidadeadministrativasuperior foreign key (fk_unidade_administrativa_superior) references unidade_administrativa (id)
);

CREATE TABLE colaborador (
    id bigserial NOT NULL,
    data_cadastro timestamp,
    data_ultima_atualizacao timestamp,
    usuario_cadastro varchar(50),
    usuario_ultima_atualizacao varchar(50),
    nome text not null,
    cpf bigint not null,
    telefone varchar(15),
    email varchar(50),
    cep bigint,
    logradouro text,
    complemento text,
    numero_logradouro bigint,
    bairro text,
    cidade text,
    estado varchar(2),
    fk_cargo bigint,
    fk_upload UUID,
    primary key (id),
    constraint fk_colaborador_cargo foreign key (fk_cargo) references cargo (id)
);

CREATE TABLE colaborador_unidade_administrativa (
  fk_colaborador bigint not null,
  fk_unidade_administrativa bigint not null,
  constraint fk_colaboradorunidadeadministrativa_colaborador foreign key (fk_colaborador) references colaborador (id),
  constraint fk_colaboradorunidadeadministrativa_unidadeadministrativa foreign key (fk_unidade_administrativa) references unidade_administrativa (id)
);