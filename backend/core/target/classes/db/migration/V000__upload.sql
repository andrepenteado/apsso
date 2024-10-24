DROP TABLE IF EXISTS Upload CASCADE;

CREATE TABLE IF NOT EXISTS Upload (
  UUID       UUID         NOT NULL,
  Nome       TEXT         NOT NULL,
  Descricao  TEXT         NULL,
  Tipo_Mime  TEXT         NOT NULL,
  Tamanho    BIGINT       NOT NULL,
  Base64     TEXT         NOT NULL,
  CONSTRAINT PK_Upload    PRIMARY KEY (UUID)
);
