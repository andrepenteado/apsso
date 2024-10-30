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
    fk_upload UUID NULL
);
create unique index un_users_cpf on users (cpf);

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
