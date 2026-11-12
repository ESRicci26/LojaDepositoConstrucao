--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

-- Started on 2026-11-12 14:13:28

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 200 (class 1259 OID 74969)
-- Name: tb_administrador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_administrador (
    no_login character varying(100) NOT NULL,
    no_senha character varying(100) NOT NULL
);


ALTER TABLE public.tb_administrador OWNER TO postgres;

--
-- TOC entry 3105 (class 0 OID 0)
-- Dependencies: 200
-- Name: TABLE tb_administrador; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_administrador IS 'Administradores com acesso ao sistema.';


--
-- TOC entry 3106 (class 0 OID 0)
-- Dependencies: 200
-- Name: COLUMN tb_administrador.no_login; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_administrador.no_login IS 'Login do administrador.';


--
-- TOC entry 3107 (class 0 OID 0)
-- Dependencies: 200
-- Name: COLUMN tb_administrador.no_senha; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_administrador.no_senha IS 'Hash BCrypt da senha do administrador.';


--
-- TOC entry 202 (class 1259 OID 74976)
-- Name: tb_cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_cliente (
    id_cliente integer NOT NULL,
    no_cliente character varying(155) NOT NULL,
    nr_rg bigint NOT NULL,
    nr_cpf character(11) NOT NULL,
    tp_cliente character varying(10) NOT NULL,
    CONSTRAINT ck_tb_cliente_tipo CHECK (((tp_cliente)::text = ANY ((ARRAY['NOVO'::character varying, 'ANTIGO'::character varying])::text[])))
);


ALTER TABLE public.tb_cliente OWNER TO postgres;

--
-- TOC entry 3108 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE tb_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_cliente IS 'Clientes da loja.';


--
-- TOC entry 3109 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN tb_cliente.id_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_cliente.id_cliente IS 'Identificador sequencial único do cliente no sistema.';


--
-- TOC entry 3110 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN tb_cliente.no_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_cliente.no_cliente IS 'Nome do cliente.';


--
-- TOC entry 3111 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN tb_cliente.nr_rg; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_cliente.nr_rg IS 'Número do RG do cliente.';


--
-- TOC entry 3112 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN tb_cliente.nr_cpf; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_cliente.nr_cpf IS 'Número do CPF do cliente (somente dígitos).';


--
-- TOC entry 3113 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN tb_cliente.tp_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_cliente.tp_cliente IS 'Tipo do cliente (NOVO ou ANTIGO).';


--
-- TOC entry 201 (class 1259 OID 74974)
-- Name: tb_cliente_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_cliente ALTER COLUMN id_cliente ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_cliente_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 204 (class 1259 OID 74988)
-- Name: tb_endereco_cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_endereco_cliente (
    id_endereco_cliente integer NOT NULL,
    id_cliente integer NOT NULL,
    sg_uf character(2) NOT NULL,
    no_endereco character varying(150) NOT NULL,
    no_cidade character varying(100) NOT NULL
);


ALTER TABLE public.tb_endereco_cliente OWNER TO postgres;

--
-- TOC entry 3114 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE tb_endereco_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_endereco_cliente IS 'Endereços dos clientes.';


--
-- TOC entry 3115 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN tb_endereco_cliente.sg_uf; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_endereco_cliente.sg_uf IS 'Sigla da Unidade Federativa.';


--
-- TOC entry 3116 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN tb_endereco_cliente.no_endereco; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_endereco_cliente.no_endereco IS 'Nome do endereço do cliente.';


--
-- TOC entry 3117 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN tb_endereco_cliente.no_cidade; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_endereco_cliente.no_cidade IS 'Nome da cidade do cliente.';


--
-- TOC entry 203 (class 1259 OID 74986)
-- Name: tb_endereco_cliente_id_endereco_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_endereco_cliente ALTER COLUMN id_endereco_cliente ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_endereco_cliente_id_endereco_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 210 (class 1259 OID 75025)
-- Name: tb_endereco_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_endereco_loja (
    id_endereco_loja integer NOT NULL,
    id_loja integer NOT NULL,
    sg_uf character(2) NOT NULL,
    no_endereco character varying(150) NOT NULL,
    no_cidade character varying(100) NOT NULL
);


ALTER TABLE public.tb_endereco_loja OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 75023)
-- Name: tb_endereco_loja_id_endereco_loja_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_endereco_loja ALTER COLUMN id_endereco_loja ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_endereco_loja_id_endereco_loja_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 208 (class 1259 OID 75018)
-- Name: tb_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_loja (
    id_loja integer NOT NULL,
    no_loja character varying(100) NOT NULL,
    nr_cnpj character varying(25) NOT NULL
);


ALTER TABLE public.tb_loja OWNER TO postgres;

--
-- TOC entry 3118 (class 0 OID 0)
-- Dependencies: 208
-- Name: TABLE tb_loja; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_loja IS 'Lojas cadastradas.';


--
-- TOC entry 3119 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN tb_loja.no_loja; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_loja.no_loja IS 'Nome da loja.';


--
-- TOC entry 3120 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN tb_loja.nr_cnpj; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_loja.nr_cnpj IS 'Número do CNPJ da loja.';


--
-- TOC entry 207 (class 1259 OID 75016)
-- Name: tb_loja_id_loja_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_loja ALTER COLUMN id_loja ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_loja_id_loja_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 214 (class 1259 OID 75051)
-- Name: tb_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_produto (
    cd_produto integer NOT NULL,
    id_loja integer NOT NULL,
    no_produto character varying(200) NOT NULL,
    qt_produto_estoque integer NOT NULL,
    vl_produto_unitario numeric(12,2) NOT NULL,
    CONSTRAINT ck_produto_estoque CHECK ((qt_produto_estoque >= 0)),
    CONSTRAINT ck_produto_valor CHECK ((vl_produto_unitario >= (0)::numeric))
);


ALTER TABLE public.tb_produto OWNER TO postgres;

--
-- TOC entry 3121 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE tb_produto; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_produto IS 'Produtos em estoque.';


--
-- TOC entry 3122 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN tb_produto.cd_produto; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_produto.cd_produto IS 'Código único sequencial do produto.';


--
-- TOC entry 3123 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN tb_produto.id_loja; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_produto.id_loja IS 'Identificador da loja dona do produto.';


--
-- TOC entry 3124 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN tb_produto.no_produto; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_produto.no_produto IS 'Nome do produto.';


--
-- TOC entry 3125 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN tb_produto.qt_produto_estoque; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_produto.qt_produto_estoque IS 'Quantidade disponível do produto.';


--
-- TOC entry 3126 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN tb_produto.vl_produto_unitario; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_produto.vl_produto_unitario IS 'Valor por unidade do produto.';


--
-- TOC entry 213 (class 1259 OID 75049)
-- Name: tb_produto_cd_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_produto ALTER COLUMN cd_produto ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_produto_cd_produto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 75080)
-- Name: tb_produto_venda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_produto_venda (
    id_produto_venda integer NOT NULL,
    id_venda integer NOT NULL,
    cd_produto integer NOT NULL,
    qt_produto_retirado integer NOT NULL,
    vl_total_produto numeric(12,2) NOT NULL,
    CONSTRAINT ck_produto_venda_qtd CHECK ((qt_produto_retirado > 0))
);


ALTER TABLE public.tb_produto_venda OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 75078)
-- Name: tb_produto_venda_id_produto_venda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_produto_venda ALTER COLUMN id_produto_venda ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_produto_venda_id_produto_venda_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 206 (class 1259 OID 75003)
-- Name: tb_telefone_cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_telefone_cliente (
    id_telefone_cliente integer NOT NULL,
    id_cliente integer NOT NULL,
    nr_telefone character varying(20) NOT NULL
);


ALTER TABLE public.tb_telefone_cliente OWNER TO postgres;

--
-- TOC entry 3127 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE tb_telefone_cliente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_telefone_cliente IS 'Telefones dos clientes.';


--
-- TOC entry 3128 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN tb_telefone_cliente.nr_telefone; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_telefone_cliente.nr_telefone IS 'Número de telefone do cliente.';


--
-- TOC entry 205 (class 1259 OID 75001)
-- Name: tb_telefone_cliente_id_telefone_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_telefone_cliente ALTER COLUMN id_telefone_cliente ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_telefone_cliente_id_telefone_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 212 (class 1259 OID 75038)
-- Name: tb_telefone_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_telefone_loja (
    id_telefone_loja integer NOT NULL,
    id_loja integer NOT NULL,
    nr_telefone character varying(20) NOT NULL
);


ALTER TABLE public.tb_telefone_loja OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 75036)
-- Name: tb_telefone_loja_id_telefone_loja_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_telefone_loja ALTER COLUMN id_telefone_loja ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_telefone_loja_id_telefone_loja_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 75066)
-- Name: tb_venda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_venda (
    id_venda integer NOT NULL,
    id_cliente integer NOT NULL,
    dh_pagamento date,
    vl_total_venda numeric(12,2) NOT NULL,
    ic_pago boolean DEFAULT false NOT NULL
);


ALTER TABLE public.tb_venda OWNER TO postgres;

--
-- TOC entry 3129 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE tb_venda; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tb_venda IS 'Vendas realizadas.';


--
-- TOC entry 3130 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN tb_venda.dh_pagamento; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_venda.dh_pagamento IS 'Data do pagamento da compra (nula quando não paga).';


--
-- TOC entry 3131 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN tb_venda.vl_total_venda; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_venda.vl_total_venda IS 'Valor total da retirada/compra dos produtos.';


--
-- TOC entry 3132 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN tb_venda.ic_pago; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.tb_venda.ic_pago IS 'Indicador de pagamento: FALSE = não pago, TRUE = pago.';


--
-- TOC entry 215 (class 1259 OID 75064)
-- Name: tb_venda_id_venda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tb_venda ALTER COLUMN id_venda ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tb_venda_id_venda_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3081 (class 0 OID 74969)
-- Dependencies: 200
-- Data for Name: tb_administrador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_administrador (no_login, no_senha) FROM stdin;
admin123	$2a$10$FbOHkg7UNcGknga9.R3Ur.31FqVQCA65gdVQxNzBQaUXK2TGnm/Sy
\.


--
-- TOC entry 3083 (class 0 OID 74976)
-- Dependencies: 202
-- Data for Name: tb_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_cliente (id_cliente, no_cliente, nr_rg, nr_cpf, tp_cliente) FROM stdin;
1	Carolina Ferraz Lages	123456789	12345678901	NOVO
2	Ricardo da Cunha Silveira	201214987	10987654321	ANTIGO
3	Marcos Corrêa Vaz	456789123	45678912300	NOVO
4	Marina Lages Ricci	321654987	32165498700	ANTIGO
5	Guilherme Santos Dumont	789123456	78912345600	NOVO
\.


--
-- TOC entry 3085 (class 0 OID 74988)
-- Dependencies: 204
-- Data for Name: tb_endereco_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_endereco_cliente (id_endereco_cliente, id_cliente, sg_uf, no_endereco, no_cidade) FROM stdin;
6	1	SP	Av. Paulista, 2073	São Paulo
7	2	SP	Rua Pássaros e Flores, 99	São Paulo
8	3	SP	Rua Pascal, 25	São Paulo
9	4	SP	Av. Brigadeiro Faria Lima, 201	São Paulo
10	5	SP	Av. Jornalista Roberto Marins, 2258	São Paulo
\.


--
-- TOC entry 3091 (class 0 OID 75025)
-- Dependencies: 210
-- Data for Name: tb_endereco_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_endereco_loja (id_endereco_loja, id_loja, sg_uf, no_endereco, no_cidade) FROM stdin;
1	1	DF	Setor Comercial Sul	Brasília
\.


--
-- TOC entry 3089 (class 0 OID 75018)
-- Dependencies: 208
-- Data for Name: tb_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_loja (id_loja, no_loja, nr_cnpj) FROM stdin;
1	Materiais de Construção Ltda.	00.000.000/000-1
\.


--
-- TOC entry 3095 (class 0 OID 75051)
-- Dependencies: 214
-- Data for Name: tb_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_produto (cd_produto, id_loja, no_produto, qt_produto_estoque, vl_produto_unitario) FROM stdin;
2	1	Tijolo	500	0.80
3	1	Areia Fina 20kg	200	12.50
4	1	Prego 15mm	1000	0.05
1	1	Cimento 50kg	98	30.00
5	1	Bloco de Concreto	290	2.50
6	1	Massa Corrida (Saco 5 KG)	120	18.50
\.


--
-- TOC entry 3099 (class 0 OID 75080)
-- Dependencies: 218
-- Data for Name: tb_produto_venda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_produto_venda (id_produto_venda, id_venda, cd_produto, qt_produto_retirado, vl_total_produto) FROM stdin;
1	1	1	5	150.00
2	2	2	300	240.00
3	2	3	10	60.00
4	4	4	100	5.00
5	5	5	20	50.00
6	6	1	2	60.00
7	6	5	10	25.00
\.


--
-- TOC entry 3087 (class 0 OID 75003)
-- Dependencies: 206
-- Data for Name: tb_telefone_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_telefone_cliente (id_telefone_cliente, id_cliente, nr_telefone) FROM stdin;
6	1	(11) 98765-4321
7	2	(11) 97776-5432
8	3	(31) 91234-5678
9	4	(11) 88999-1111
10	5	(11) 97788-2222
\.


--
-- TOC entry 3093 (class 0 OID 75038)
-- Dependencies: 212
-- Data for Name: tb_telefone_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_telefone_loja (id_telefone_loja, id_loja, nr_telefone) FROM stdin;
1	1	(61) 99999-9999
\.


--
-- TOC entry 3097 (class 0 OID 75066)
-- Dependencies: 216
-- Data for Name: tb_venda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_venda (id_venda, id_cliente, dh_pagamento, vl_total_venda, ic_pago) FROM stdin;
1	1	2024-11-20	150.00	t
2	2	\N	300.00	f
3	3	2024-11-21	450.00	t
4	4	2024-11-23	200.00	t
5	5	2024-11-24	100.00	t
6	4	2026-11-12	85.00	t
\.


--
-- TOC entry 3133 (class 0 OID 0)
-- Dependencies: 201
-- Name: tb_cliente_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_cliente_id_cliente_seq', 5, true);


--
-- TOC entry 3134 (class 0 OID 0)
-- Dependencies: 203
-- Name: tb_endereco_cliente_id_endereco_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_endereco_cliente_id_endereco_cliente_seq', 10, true);


--
-- TOC entry 3135 (class 0 OID 0)
-- Dependencies: 209
-- Name: tb_endereco_loja_id_endereco_loja_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_endereco_loja_id_endereco_loja_seq', 1, true);


--
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 207
-- Name: tb_loja_id_loja_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_loja_id_loja_seq', 1, true);


--
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 213
-- Name: tb_produto_cd_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_produto_cd_produto_seq', 6, true);


--
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 217
-- Name: tb_produto_venda_id_produto_venda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_produto_venda_id_produto_venda_seq', 7, true);


--
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 205
-- Name: tb_telefone_cliente_id_telefone_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_telefone_cliente_id_telefone_cliente_seq', 10, true);


--
-- TOC entry 3140 (class 0 OID 0)
-- Dependencies: 211
-- Name: tb_telefone_loja_id_telefone_loja_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_telefone_loja_id_telefone_loja_seq', 1, true);


--
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 215
-- Name: tb_venda_id_venda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_venda_id_venda_seq', 6, true);


--
-- TOC entry 2908 (class 2606 OID 74973)
-- Name: tb_administrador pk_tb_administrador; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_administrador
    ADD CONSTRAINT pk_tb_administrador PRIMARY KEY (no_login);


--
-- TOC entry 2910 (class 2606 OID 74981)
-- Name: tb_cliente pk_tb_cliente; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_cliente
    ADD CONSTRAINT pk_tb_cliente PRIMARY KEY (id_cliente);


--
-- TOC entry 2917 (class 2606 OID 74992)
-- Name: tb_endereco_cliente pk_tb_endereco_cliente; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_endereco_cliente
    ADD CONSTRAINT pk_tb_endereco_cliente PRIMARY KEY (id_endereco_cliente);


--
-- TOC entry 2929 (class 2606 OID 75029)
-- Name: tb_endereco_loja pk_tb_endereco_loja; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_endereco_loja
    ADD CONSTRAINT pk_tb_endereco_loja PRIMARY KEY (id_endereco_loja);


--
-- TOC entry 2926 (class 2606 OID 75022)
-- Name: tb_loja pk_tb_loja; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_loja
    ADD CONSTRAINT pk_tb_loja PRIMARY KEY (id_loja);


--
-- TOC entry 2935 (class 2606 OID 75057)
-- Name: tb_produto pk_tb_produto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_produto
    ADD CONSTRAINT pk_tb_produto PRIMARY KEY (cd_produto);


--
-- TOC entry 2942 (class 2606 OID 75085)
-- Name: tb_produto_venda pk_tb_produto_venda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_produto_venda
    ADD CONSTRAINT pk_tb_produto_venda PRIMARY KEY (id_produto_venda);


--
-- TOC entry 2922 (class 2606 OID 75007)
-- Name: tb_telefone_cliente pk_tb_telefone_cliente; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_telefone_cliente
    ADD CONSTRAINT pk_tb_telefone_cliente PRIMARY KEY (id_telefone_cliente);


--
-- TOC entry 2932 (class 2606 OID 75042)
-- Name: tb_telefone_loja pk_tb_telefone_loja; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_telefone_loja
    ADD CONSTRAINT pk_tb_telefone_loja PRIMARY KEY (id_telefone_loja);


--
-- TOC entry 2938 (class 2606 OID 75071)
-- Name: tb_venda pk_tb_venda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_venda
    ADD CONSTRAINT pk_tb_venda PRIMARY KEY (id_venda);


--
-- TOC entry 2912 (class 2606 OID 74983)
-- Name: tb_cliente uk_tb_cliente_cpf; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_cliente
    ADD CONSTRAINT uk_tb_cliente_cpf UNIQUE (nr_cpf);


--
-- TOC entry 2914 (class 2606 OID 74985)
-- Name: tb_cliente uk_tb_cliente_rg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_cliente
    ADD CONSTRAINT uk_tb_cliente_rg UNIQUE (nr_rg);


--
-- TOC entry 2919 (class 2606 OID 74994)
-- Name: tb_endereco_cliente uk_tb_endereco_cliente; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_endereco_cliente
    ADD CONSTRAINT uk_tb_endereco_cliente UNIQUE (no_endereco);


--
-- TOC entry 2924 (class 2606 OID 75009)
-- Name: tb_telefone_cliente uk_tb_telefone_cliente; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_telefone_cliente
    ADD CONSTRAINT uk_tb_telefone_cliente UNIQUE (nr_telefone);


--
-- TOC entry 2915 (class 1259 OID 75000)
-- Name: idx_endereco_cliente_cliente; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_endereco_cliente_cliente ON public.tb_endereco_cliente USING btree (id_cliente);


--
-- TOC entry 2927 (class 1259 OID 75035)
-- Name: idx_endereco_loja_loja; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_endereco_loja_loja ON public.tb_endereco_loja USING btree (id_loja);


--
-- TOC entry 2933 (class 1259 OID 75063)
-- Name: idx_produto_loja; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_produto_loja ON public.tb_produto USING btree (id_loja);


--
-- TOC entry 2939 (class 1259 OID 75096)
-- Name: idx_produto_venda_produto; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_produto_venda_produto ON public.tb_produto_venda USING btree (cd_produto);


--
-- TOC entry 2940 (class 1259 OID 75097)
-- Name: idx_produto_venda_venda; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_produto_venda_venda ON public.tb_produto_venda USING btree (id_venda);


--
-- TOC entry 2920 (class 1259 OID 75015)
-- Name: idx_telefone_cliente_cliente; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_telefone_cliente_cliente ON public.tb_telefone_cliente USING btree (id_cliente);


--
-- TOC entry 2930 (class 1259 OID 75048)
-- Name: idx_telefone_loja_loja; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_telefone_loja_loja ON public.tb_telefone_loja USING btree (id_loja);


--
-- TOC entry 2936 (class 1259 OID 75077)
-- Name: idx_venda_cliente; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_venda_cliente ON public.tb_venda USING btree (id_cliente);


--
-- TOC entry 2943 (class 2606 OID 74995)
-- Name: tb_endereco_cliente fk_endereco_cliente_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_endereco_cliente
    ADD CONSTRAINT fk_endereco_cliente_cliente FOREIGN KEY (id_cliente) REFERENCES public.tb_cliente(id_cliente);


--
-- TOC entry 2945 (class 2606 OID 75030)
-- Name: tb_endereco_loja fk_endereco_loja_loja; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_endereco_loja
    ADD CONSTRAINT fk_endereco_loja_loja FOREIGN KEY (id_loja) REFERENCES public.tb_loja(id_loja);


--
-- TOC entry 2947 (class 2606 OID 75058)
-- Name: tb_produto fk_produto_loja; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_produto
    ADD CONSTRAINT fk_produto_loja FOREIGN KEY (id_loja) REFERENCES public.tb_loja(id_loja);


--
-- TOC entry 2949 (class 2606 OID 75086)
-- Name: tb_produto_venda fk_produto_venda_produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_produto_venda
    ADD CONSTRAINT fk_produto_venda_produto FOREIGN KEY (cd_produto) REFERENCES public.tb_produto(cd_produto);


--
-- TOC entry 2950 (class 2606 OID 75091)
-- Name: tb_produto_venda fk_produto_venda_venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_produto_venda
    ADD CONSTRAINT fk_produto_venda_venda FOREIGN KEY (id_venda) REFERENCES public.tb_venda(id_venda);


--
-- TOC entry 2944 (class 2606 OID 75010)
-- Name: tb_telefone_cliente fk_telefone_cliente_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_telefone_cliente
    ADD CONSTRAINT fk_telefone_cliente_cliente FOREIGN KEY (id_cliente) REFERENCES public.tb_cliente(id_cliente);


--
-- TOC entry 2946 (class 2606 OID 75043)
-- Name: tb_telefone_loja fk_telefone_loja_loja; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_telefone_loja
    ADD CONSTRAINT fk_telefone_loja_loja FOREIGN KEY (id_loja) REFERENCES public.tb_loja(id_loja);


--
-- TOC entry 2948 (class 2606 OID 75072)
-- Name: tb_venda fk_venda_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_venda
    ADD CONSTRAINT fk_venda_cliente FOREIGN KEY (id_cliente) REFERENCES public.tb_cliente(id_cliente);


-- Completed on 2026-11-12 14:13:28

--
-- PostgreSQL database dump complete
--

