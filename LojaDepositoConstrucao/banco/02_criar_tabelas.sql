-- =====================================================================
-- Estrutura das tabelas e dados essenciais (PostgreSQL)
-- Execute conectado ao banco "lojadepositoconstrucao":
--
--   psql -U postgres -d lojadepositoconstrucao -f banco/02_criar_tabelas.sql
-- =====================================================================

-- ---------------------------------------------------------------------
-- TB_ADMINISTRADOR
-- A senha é armazenada como hash BCrypt (nunca em texto puro).
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_administrador (
    no_login VARCHAR(100) NOT NULL,
    no_senha VARCHAR(100) NOT NULL,
    CONSTRAINT pk_tb_administrador PRIMARY KEY (no_login)
);
COMMENT ON TABLE  tb_administrador          IS 'Administradores com acesso ao sistema.';
COMMENT ON COLUMN tb_administrador.no_login IS 'Login do administrador.';
COMMENT ON COLUMN tb_administrador.no_senha IS 'Hash BCrypt da senha do administrador.';

-- ---------------------------------------------------------------------
-- TB_CLIENTE
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_cliente (
    id_cliente INTEGER GENERATED ALWAYS AS IDENTITY,
    no_cliente VARCHAR(155) NOT NULL,
    nr_rg      BIGINT       NOT NULL,
    nr_cpf     CHAR(11)     NOT NULL,
    tp_cliente VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_tb_cliente        PRIMARY KEY (id_cliente),
    CONSTRAINT uk_tb_cliente_cpf    UNIQUE (nr_cpf),
    CONSTRAINT uk_tb_cliente_rg     UNIQUE (nr_rg),
    CONSTRAINT ck_tb_cliente_tipo   CHECK (tp_cliente IN ('NOVO', 'ANTIGO'))
);
COMMENT ON TABLE  tb_cliente            IS 'Clientes da loja.';
COMMENT ON COLUMN tb_cliente.id_cliente IS 'Identificador sequencial único do cliente no sistema.';
COMMENT ON COLUMN tb_cliente.no_cliente IS 'Nome do cliente.';
COMMENT ON COLUMN tb_cliente.nr_rg      IS 'Número do RG do cliente.';
COMMENT ON COLUMN tb_cliente.nr_cpf     IS 'Número do CPF do cliente (somente dígitos).';
COMMENT ON COLUMN tb_cliente.tp_cliente IS 'Tipo do cliente (NOVO ou ANTIGO).';

-- ---------------------------------------------------------------------
-- TB_ENDERECO_CLIENTE
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_endereco_cliente (
    id_endereco_cliente INTEGER GENERATED ALWAYS AS IDENTITY,
    id_cliente          INTEGER      NOT NULL,
    sg_uf               CHAR(2)      NOT NULL,
    no_endereco         VARCHAR(150) NOT NULL,
    no_cidade           VARCHAR(100) NOT NULL,
    CONSTRAINT pk_tb_endereco_cliente  PRIMARY KEY (id_endereco_cliente),
    CONSTRAINT uk_tb_endereco_cliente  UNIQUE (no_endereco),
    CONSTRAINT fk_endereco_cliente_cliente
        FOREIGN KEY (id_cliente) REFERENCES tb_cliente (id_cliente)
);
CREATE INDEX IF NOT EXISTS idx_endereco_cliente_cliente ON tb_endereco_cliente (id_cliente);
COMMENT ON TABLE  tb_endereco_cliente             IS 'Endereços dos clientes.';
COMMENT ON COLUMN tb_endereco_cliente.sg_uf       IS 'Sigla da Unidade Federativa.';
COMMENT ON COLUMN tb_endereco_cliente.no_endereco IS 'Nome do endereço do cliente.';
COMMENT ON COLUMN tb_endereco_cliente.no_cidade   IS 'Nome da cidade do cliente.';

-- ---------------------------------------------------------------------
-- TB_TELEFONE_CLIENTE
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_telefone_cliente (
    id_telefone_cliente INTEGER GENERATED ALWAYS AS IDENTITY,
    id_cliente          INTEGER     NOT NULL,
    nr_telefone         VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tb_telefone_cliente  PRIMARY KEY (id_telefone_cliente),
    CONSTRAINT uk_tb_telefone_cliente  UNIQUE (nr_telefone),
    CONSTRAINT fk_telefone_cliente_cliente
        FOREIGN KEY (id_cliente) REFERENCES tb_cliente (id_cliente)
);
CREATE INDEX IF NOT EXISTS idx_telefone_cliente_cliente ON tb_telefone_cliente (id_cliente);
COMMENT ON TABLE  tb_telefone_cliente             IS 'Telefones dos clientes.';
COMMENT ON COLUMN tb_telefone_cliente.nr_telefone IS 'Número de telefone do cliente.';

-- ---------------------------------------------------------------------
-- TB_LOJA
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_loja (
    id_loja INTEGER GENERATED ALWAYS AS IDENTITY,
    no_loja VARCHAR(100) NOT NULL,
    nr_cnpj VARCHAR(25)  NOT NULL,
    CONSTRAINT pk_tb_loja PRIMARY KEY (id_loja)
);
COMMENT ON TABLE  tb_loja         IS 'Lojas cadastradas.';
COMMENT ON COLUMN tb_loja.no_loja IS 'Nome da loja.';
COMMENT ON COLUMN tb_loja.nr_cnpj IS 'Número do CNPJ da loja.';

-- ---------------------------------------------------------------------
-- TB_ENDERECO_LOJA
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_endereco_loja (
    id_endereco_loja INTEGER GENERATED ALWAYS AS IDENTITY,
    id_loja          INTEGER      NOT NULL,
    sg_uf            CHAR(2)      NOT NULL,
    no_endereco      VARCHAR(150) NOT NULL,
    no_cidade        VARCHAR(100) NOT NULL,
    CONSTRAINT pk_tb_endereco_loja PRIMARY KEY (id_endereco_loja),
    CONSTRAINT fk_endereco_loja_loja
        FOREIGN KEY (id_loja) REFERENCES tb_loja (id_loja)
);
CREATE INDEX IF NOT EXISTS idx_endereco_loja_loja ON tb_endereco_loja (id_loja);

-- ---------------------------------------------------------------------
-- TB_TELEFONE_LOJA
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_telefone_loja (
    id_telefone_loja INTEGER GENERATED ALWAYS AS IDENTITY,
    id_loja          INTEGER     NOT NULL,
    nr_telefone      VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tb_telefone_loja PRIMARY KEY (id_telefone_loja),
    CONSTRAINT fk_telefone_loja_loja
        FOREIGN KEY (id_loja) REFERENCES tb_loja (id_loja)
);
CREATE INDEX IF NOT EXISTS idx_telefone_loja_loja ON tb_telefone_loja (id_loja);

-- ---------------------------------------------------------------------
-- TB_PRODUTO
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_produto (
    cd_produto          INTEGER GENERATED ALWAYS AS IDENTITY,
    id_loja             INTEGER       NOT NULL,
    no_produto          VARCHAR(200)  NOT NULL,
    qt_produto_estoque  INTEGER       NOT NULL,
    vl_produto_unitario NUMERIC(12,2) NOT NULL,
    CONSTRAINT pk_tb_produto         PRIMARY KEY (cd_produto),
    CONSTRAINT ck_produto_estoque    CHECK (qt_produto_estoque >= 0),
    CONSTRAINT ck_produto_valor      CHECK (vl_produto_unitario >= 0),
    CONSTRAINT fk_produto_loja
        FOREIGN KEY (id_loja) REFERENCES tb_loja (id_loja)
);
CREATE INDEX IF NOT EXISTS idx_produto_loja ON tb_produto (id_loja);
COMMENT ON TABLE  tb_produto                     IS 'Produtos em estoque.';
COMMENT ON COLUMN tb_produto.cd_produto          IS 'Código único sequencial do produto.';
COMMENT ON COLUMN tb_produto.id_loja             IS 'Identificador da loja dona do produto.';
COMMENT ON COLUMN tb_produto.no_produto          IS 'Nome do produto.';
COMMENT ON COLUMN tb_produto.qt_produto_estoque  IS 'Quantidade disponível do produto.';
COMMENT ON COLUMN tb_produto.vl_produto_unitario IS 'Valor por unidade do produto.';

-- ---------------------------------------------------------------------
-- TB_VENDA
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_venda (
    id_venda       INTEGER GENERATED ALWAYS AS IDENTITY,
    id_cliente     INTEGER       NOT NULL,
    dh_pagamento   DATE,
    vl_total_venda NUMERIC(12,2) NOT NULL,
    ic_pago        BOOLEAN       NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_tb_venda PRIMARY KEY (id_venda),
    CONSTRAINT fk_venda_cliente
        FOREIGN KEY (id_cliente) REFERENCES tb_cliente (id_cliente)
);
CREATE INDEX IF NOT EXISTS idx_venda_cliente ON tb_venda (id_cliente);
COMMENT ON TABLE  tb_venda                IS 'Vendas realizadas.';
COMMENT ON COLUMN tb_venda.dh_pagamento   IS 'Data do pagamento da compra (nula quando não paga).';
COMMENT ON COLUMN tb_venda.vl_total_venda IS 'Valor total da retirada/compra dos produtos.';
COMMENT ON COLUMN tb_venda.ic_pago        IS 'Indicador de pagamento: FALSE = não pago, TRUE = pago.';

-- ---------------------------------------------------------------------
-- TB_PRODUTO_VENDA
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_produto_venda (
    id_produto_venda    INTEGER GENERATED ALWAYS AS IDENTITY,
    id_venda            INTEGER       NOT NULL,
    cd_produto          INTEGER       NOT NULL,
    qt_produto_retirado INTEGER       NOT NULL,
    vl_total_produto    NUMERIC(12,2) NOT NULL,
    CONSTRAINT pk_tb_produto_venda  PRIMARY KEY (id_produto_venda),
    CONSTRAINT ck_produto_venda_qtd CHECK (qt_produto_retirado > 0),
    CONSTRAINT fk_produto_venda_produto
        FOREIGN KEY (cd_produto) REFERENCES tb_produto (cd_produto),
    CONSTRAINT fk_produto_venda_venda
        FOREIGN KEY (id_venda) REFERENCES tb_venda (id_venda)
);
CREATE INDEX IF NOT EXISTS idx_produto_venda_produto ON tb_produto_venda (cd_produto);
CREATE INDEX IF NOT EXISTS idx_produto_venda_venda   ON tb_produto_venda (id_venda);

-- =====================================================================
-- DADOS ESSENCIAIS
-- =====================================================================

-- Loja padrão
INSERT INTO tb_loja (no_loja, nr_cnpj)
SELECT 'Materiais de Construção Ltda.', '00.000.000/000-1'
WHERE NOT EXISTS (SELECT 1 FROM tb_loja);

INSERT INTO tb_endereco_loja (id_loja, sg_uf, no_endereco, no_cidade)
SELECT l.id_loja, 'DF', 'Setor Comercial Sul', 'Brasília'
FROM tb_loja l
WHERE l.no_loja = 'Materiais de Construção Ltda.'
  AND NOT EXISTS (SELECT 1 FROM tb_endereco_loja);

INSERT INTO tb_telefone_loja (id_loja, nr_telefone)
SELECT l.id_loja, '(61) 99999-9999'
FROM tb_loja l
WHERE l.no_loja = 'Materiais de Construção Ltda.'
  AND NOT EXISTS (SELECT 1 FROM tb_telefone_loja);

-- Administrador padrão: login "admin123" / senha "admin123" (hash BCrypt).
-- Altere a senha após o primeiro acesso em ambiente real.
INSERT INTO tb_administrador (no_login, no_senha)
VALUES ('admin123', '$2a$10$FbOHkg7UNcGknga9.R3Ur.31FqVQCA65gdVQxNzBQaUXK2TGnm/Sy')
ON CONFLICT (no_login) DO NOTHING;
