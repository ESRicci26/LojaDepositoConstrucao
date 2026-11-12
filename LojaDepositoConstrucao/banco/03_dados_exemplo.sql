-- =====================================================================
-- Dados de exemplo (opcional)
-- Execute após o script 02, conectado ao banco "lojadepositoconstrucao":
--
--   psql -U postgres -d lojadepositoconstrucao -f banco/03_dados_exemplo.sql
-- =====================================================================

INSERT INTO tb_cliente (no_cliente, nr_rg, nr_cpf, tp_cliente) VALUES
    ('Carolina Ferraz Lages',     123456789, '12345678901', 'NOVO'),
    ('Ricardo da Cunha Silveira', 987654321, '10987654321', 'ANTIGO'),
    ('Marcos Corrêa Vaz',   456789123, '45678912300', 'NOVO'),
    ('Marina Lages Ricci',      321654987, '32165498700', 'ANTIGO'),
    ('Guilherme Santos Dumont',     789123456, '78912345600', 'NOVO');

INSERT INTO tb_endereco_cliente (id_cliente, sg_uf, no_endereco, no_cidade) VALUES
    (1, 'SP', 'Av. Paulista, 2073',          'São Paulo'),
    (2, 'SP', 'Rua Pássaros e Flores, 99',   'São Paulo'),
    (3, 'SP', 'Rua Pascal, 25',              'São Paulo'),
    (4, 'SP', 'Av. Brigadeiro Faria Lima, 201','São Paulo'),
    (5, 'SP', 'Av. Jornalista Roberto Marins, 2258', 'São Paulo');

INSERT INTO tb_telefone_cliente (id_cliente, nr_telefone) VALUES
    (1, '(11) 98765-4321'),
    (2, '(11) 97776-5432'),
    (3, '(31) 91234-5678'),
    (4, '(11) 88999-1111'),
    (5, '(11) 97788-2222');

INSERT INTO tb_produto (id_loja, no_produto, qt_produto_estoque, vl_produto_unitario) VALUES
    (1, 'Cimento 50kg',      100, 30.00),
    (1, 'Tijolo',            500,  0.80),
    (1, 'Areia Fina 20kg',   200, 12.50),
    (1, 'Prego 15mm',       1000,  0.05),
    (1, 'Bloco de Concreto', 300,  2.50);

INSERT INTO tb_venda (id_cliente, dh_pagamento, vl_total_venda, ic_pago) VALUES
    (1, '2024-11-20', 150.00, TRUE),
    (2, NULL,         300.00, FALSE),
    (3, '2024-11-21', 450.00, TRUE),
    (4, '2024-11-23', 200.00, TRUE),
    (5, '2024-11-24', 100.00, TRUE);

INSERT INTO tb_produto_venda (id_venda, cd_produto, qt_produto_retirado, vl_total_produto) VALUES
    (1, 1,   5, 150.00),
    (2, 2, 300, 240.00),
    (2, 3,  10,  60.00),
    (4, 4, 100,   5.00),
    (5, 5,  20,  50.00);

select * from tb_cliente
select * from tb_endereco_cliente
select * from tb_telefone_cliente
select * from tb_produto
select * from tb_venda
select * from tb_produto_venda
select * from tb_administrador