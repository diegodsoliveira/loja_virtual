--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5 (Ubuntu 14.5-1.pgdg20.04+1)
-- Dumped by pg_dump version 14.2

-- Started on 2022-10-08 18:34:20

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

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 3496 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 249 (class 1255 OID 131539)
-- Name: validachavepessoa(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.validachavepessoa() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare existe integer;

begin
	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);
	if (existe <= 0) then
		existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_id);
		if (existe <= 0) then
			raise exception 'Não foi encontrado ID ou PK da pessoa para realizar a associação.';
		end if;
	end if;
	return new;
end;
$$;


--
-- TOC entry 250 (class 1255 OID 131551)
-- Name: validachavepessoafornecedor(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.validachavepessoafornecedor() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare existe integer;

begin
	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_fornecedor_id);
	if (existe <= 0) then
		existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_fornecedor_id);
		if (existe <= 0) then
			raise exception 'Não foi encontrado ID ou PK da pessoa para realizar a associação.';
		end if;
	end if;
	return new;
end;
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 123175)
-- Name: acesso; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.acesso (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


--
-- TOC entry 237 (class 1259 OID 123404)
-- Name: avaliacao_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.avaliacao_produto (
    id bigint NOT NULL,
    descricao character varying(255),
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    nota integer NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 123180)
-- Name: categoria_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.categoria_produto (
    id bigint NOT NULL,
    nome_desc character varying(255) NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 123255)
-- Name: conta_pagar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.conta_pagar (
    id bigint NOT NULL,
    data_pagamento date,
    status_conta_pagar character varying(255),
    valor_desconto numeric(19,2),
    pessoa_id bigint NOT NULL,
    pessoa_fornecedor_id bigint NOT NULL,
    data_vencimento date NOT NULL,
    descricao character varying(255) NOT NULL,
    valor_total numeric(19,2) NOT NULL
);


--
-- TOC entry 218 (class 1259 OID 123239)
-- Name: conta_receber; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.conta_receber (
    id bigint NOT NULL,
    data_pagamento date,
    status_conta_receber character varying(255),
    valor_desconto numeric(19,2),
    pessoa_id bigint NOT NULL,
    data_vencimento date NOT NULL,
    descricao character varying(255) NOT NULL,
    valor_total numeric(19,2) NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 123263)
-- Name: cupom_desconto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cupom_desconto (
    id bigint NOT NULL,
    valor_porcentagem_desconto numeric(19,2),
    valor_real_desconto numeric(19,2),
    codigo_cupom character varying(255) NOT NULL,
    validade_cupom date NOT NULL
);


--
-- TOC entry 238 (class 1259 OID 131354)
-- Name: endereco; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.endereco (
    id bigint NOT NULL,
    bairro character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    complemento character varying(255),
    numero character varying(255) NOT NULL,
    rua_logradouro character varying(255) NOT NULL,
    tipo_endereco character varying(255) NOT NULL,
    uf character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL
);


--
-- TOC entry 240 (class 1259 OID 131383)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


--
-- TOC entry 239 (class 1259 OID 131371)
-- Name: imagem_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.imagem_produto (
    id bigint NOT NULL,
    imagem_miniatura text NOT NULL,
    imagem_original text NOT NULL,
    produto_id bigint NOT NULL
);


--
-- TOC entry 234 (class 1259 OID 123377)
-- Name: item_venda_loja; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.item_venda_loja (
    id bigint NOT NULL,
    produto_id bigint NOT NULL,
    venda_compra_loja_virtual_id bigint NOT NULL,
    quantidade integer NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 123192)
-- Name: marca_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.marca_produto (
    id bigint NOT NULL,
    nome_desc character varying(255) NOT NULL
);


--
-- TOC entry 241 (class 1259 OID 131393)
-- Name: nota_fiscal_compra; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nota_fiscal_compra (
    id bigint NOT NULL,
    data_compra date NOT NULL,
    descricao_obs character varying(255),
    numero_nota character varying(255) NOT NULL,
    serie_nota character varying(255) NOT NULL,
    valor_desconto numeric(19,2),
    valor_icms numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    conta_pagar_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);


--
-- TOC entry 242 (class 1259 OID 131410)
-- Name: nota_fiscal_venda; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nota_fiscal_venda (
    id bigint NOT NULL,
    numero_nota character varying(255) NOT NULL,
    pdf text NOT NULL,
    serie character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    xml text NOT NULL,
    venda_compra_loja_virtual_id bigint NOT NULL
);


--
-- TOC entry 228 (class 1259 OID 123304)
-- Name: nota_item_produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nota_item_produto (
    id bigint NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    quantidade double precision NOT NULL
);


--
-- TOC entry 243 (class 1259 OID 131427)
-- Name: pessoa_fisica; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.pessoa_fisica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    cpf character varying(255) NOT NULL,
    data_nascimento date
);


--
-- TOC entry 244 (class 1259 OID 131434)
-- Name: pessoa_juridica; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.pessoa_juridica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    categoria character varying(255),
    cnpj character varying(255) NOT NULL,
    inscricao_estadual character varying(255) NOT NULL,
    inscricao_municipall character varying(255),
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL
);


--
-- TOC entry 245 (class 1259 OID 131441)
-- Name: produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.produto (
    id bigint NOT NULL,
    alerta_qtd_estoque boolean,
    altura double precision NOT NULL,
    ativo boolean NOT NULL,
    descricao text NOT NULL,
    largura double precision NOT NULL,
    link_video_produto character varying(255),
    nome character varying(255) NOT NULL,
    peso double precision NOT NULL,
    profundidade double precision NOT NULL,
    qtd_alerta_estoque integer,
    qtd_clique integer,
    qtd_estoque integer NOT NULL,
    tipo_unidade character varying(255) NOT NULL,
    valor_venda numeric(19,2) NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 123211)
-- Name: seq_acesso; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 236 (class 1259 OID 123398)
-- Name: seq_avaliacao_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 210 (class 1259 OID 123174)
-- Name: seq_categoria_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 222 (class 1259 OID 123262)
-- Name: seq_conta_pagar; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 219 (class 1259 OID 123248)
-- Name: seq_conta_receber; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 224 (class 1259 OID 123268)
-- Name: seq_cupom_desconto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_cupom_desconto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 215 (class 1259 OID 123212)
-- Name: seq_endereco; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 220 (class 1259 OID 123254)
-- Name: seq_forma_pagamento; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 226 (class 1259 OID 123285)
-- Name: seq_imagem_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 235 (class 1259 OID 123382)
-- Name: seq_item_venda_loja; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 209 (class 1259 OID 123168)
-- Name: seq_marca_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 227 (class 1259 OID 123298)
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 232 (class 1259 OID 123335)
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 229 (class 1259 OID 123309)
-- Name: seq_nota_item_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 216 (class 1259 OID 123213)
-- Name: seq_pessoa; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 225 (class 1259 OID 123276)
-- Name: seq_produto; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 231 (class 1259 OID 123327)
-- Name: seq_status_rastreio; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 217 (class 1259 OID 123228)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 233 (class 1259 OID 123341)
-- Name: seq_venda_compra_loja_virtual; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_venda_compra_loja_virtual
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 230 (class 1259 OID 123320)
-- Name: status_rastreio; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.status_rastreio (
    id bigint NOT NULL,
    centro_distribuicao character varying(255),
    cidade character varying(255),
    estado character varying(255),
    status character varying(255),
    venda_compra_loja_virtual_id bigint NOT NULL
);


--
-- TOC entry 246 (class 1259 OID 131468)
-- Name: usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    data_atual_senha date NOT NULL,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL
);


--
-- TOC entry 247 (class 1259 OID 131475)
-- Name: usuario_acesso; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.usuario_acesso (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);


--
-- TOC entry 248 (class 1259 OID 131478)
-- Name: venda_compra_loja_virtual; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.venda_compra_loja_virtual (
    id bigint NOT NULL,
    data_entrega date NOT NULL,
    data_venda date NOT NULL,
    dias_entrega integer NOT NULL,
    valor_desconto numeric(19,2),
    valor_frete numeric(19,2) NOT NULL,
    valor_total numeric(19,2) NOT NULL,
    cupom_desconto_id bigint,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    nota_fiscal_venda_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);


--
-- TOC entry 3453 (class 0 OID 123175)
-- Dependencies: 211
-- Data for Name: acesso; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.acesso (id, descricao) FROM stdin;
\.


--
-- TOC entry 3479 (class 0 OID 123404)
-- Dependencies: 237
-- Data for Name: avaliacao_produto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.avaliacao_produto (id, descricao, pessoa_id, produto_id, nota) FROM stdin;
\.


--
-- TOC entry 3454 (class 0 OID 123180)
-- Dependencies: 212
-- Data for Name: categoria_produto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.categoria_produto (id, nome_desc) FROM stdin;
\.


--
-- TOC entry 3463 (class 0 OID 123255)
-- Dependencies: 221
-- Data for Name: conta_pagar; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.conta_pagar (id, data_pagamento, status_conta_pagar, valor_desconto, pessoa_id, pessoa_fornecedor_id, data_vencimento, descricao, valor_total) FROM stdin;
\.


--
-- TOC entry 3460 (class 0 OID 123239)
-- Dependencies: 218
-- Data for Name: conta_receber; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.conta_receber (id, data_pagamento, status_conta_receber, valor_desconto, pessoa_id, data_vencimento, descricao, valor_total) FROM stdin;
\.


--
-- TOC entry 3465 (class 0 OID 123263)
-- Dependencies: 223
-- Data for Name: cupom_desconto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.cupom_desconto (id, valor_porcentagem_desconto, valor_real_desconto, codigo_cupom, validade_cupom) FROM stdin;
\.


--
-- TOC entry 3480 (class 0 OID 131354)
-- Dependencies: 238
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.endereco (id, bairro, cep, cidade, complemento, numero, rua_logradouro, tipo_endereco, uf, pessoa_id) FROM stdin;
\.


--
-- TOC entry 3482 (class 0 OID 131383)
-- Dependencies: 240
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.forma_pagamento (id, descricao) FROM stdin;
\.


--
-- TOC entry 3481 (class 0 OID 131371)
-- Dependencies: 239
-- Data for Name: imagem_produto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.imagem_produto (id, imagem_miniatura, imagem_original, produto_id) FROM stdin;
\.


--
-- TOC entry 3476 (class 0 OID 123377)
-- Dependencies: 234
-- Data for Name: item_venda_loja; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.item_venda_loja (id, produto_id, venda_compra_loja_virtual_id, quantidade) FROM stdin;
\.


--
-- TOC entry 3455 (class 0 OID 123192)
-- Dependencies: 213
-- Data for Name: marca_produto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.marca_produto (id, nome_desc) FROM stdin;
\.


--
-- TOC entry 3483 (class 0 OID 131393)
-- Dependencies: 241
-- Data for Name: nota_fiscal_compra; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.nota_fiscal_compra (id, data_compra, descricao_obs, numero_nota, serie_nota, valor_desconto, valor_icms, valor_total, conta_pagar_id, pessoa_id) FROM stdin;
\.


--
-- TOC entry 3484 (class 0 OID 131410)
-- Dependencies: 242
-- Data for Name: nota_fiscal_venda; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.nota_fiscal_venda (id, numero_nota, pdf, serie, tipo, xml, venda_compra_loja_virtual_id) FROM stdin;
\.


--
-- TOC entry 3470 (class 0 OID 123304)
-- Dependencies: 228
-- Data for Name: nota_item_produto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.nota_item_produto (id, nota_fiscal_compra_id, produto_id, quantidade) FROM stdin;
\.


--
-- TOC entry 3485 (class 0 OID 131427)
-- Dependencies: 243
-- Data for Name: pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento) FROM stdin;
\.


--
-- TOC entry 3486 (class 0 OID 131434)
-- Dependencies: 244
-- Data for Name: pessoa_juridica; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.pessoa_juridica (id, email, nome, telefone, categoria, cnpj, inscricao_estadual, inscricao_municipall, nome_fantasia, razao_social) FROM stdin;
\.


--
-- TOC entry 3487 (class 0 OID 131441)
-- Dependencies: 245
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.produto (id, alerta_qtd_estoque, altura, ativo, descricao, largura, link_video_produto, nome, peso, profundidade, qtd_alerta_estoque, qtd_clique, qtd_estoque, tipo_unidade, valor_venda) FROM stdin;
\.


--
-- TOC entry 3472 (class 0 OID 123320)
-- Dependencies: 230
-- Data for Name: status_rastreio; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.status_rastreio (id, centro_distribuicao, cidade, estado, status, venda_compra_loja_virtual_id) FROM stdin;
\.


--
-- TOC entry 3488 (class 0 OID 131468)
-- Dependencies: 246
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.usuario (id, data_atual_senha, login, senha, pessoa_id) FROM stdin;
\.


--
-- TOC entry 3489 (class 0 OID 131475)
-- Dependencies: 247
-- Data for Name: usuario_acesso; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.usuario_acesso (usuario_id, acesso_id) FROM stdin;
\.


--
-- TOC entry 3490 (class 0 OID 131478)
-- Dependencies: 248
-- Data for Name: venda_compra_loja_virtual; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.venda_compra_loja_virtual (id, data_entrega, data_venda, dias_entrega, valor_desconto, valor_frete, valor_total, cupom_desconto_id, endereco_cobranca_id, endereco_entrega_id, forma_pagamento_id, nota_fiscal_venda_id, pessoa_id) FROM stdin;
\.


--
-- TOC entry 3497 (class 0 OID 0)
-- Dependencies: 214
-- Name: seq_acesso; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_acesso', 1, false);


--
-- TOC entry 3498 (class 0 OID 0)
-- Dependencies: 236
-- Name: seq_avaliacao_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_avaliacao_produto', 1, false);


--
-- TOC entry 3499 (class 0 OID 0)
-- Dependencies: 210
-- Name: seq_categoria_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);


--
-- TOC entry 3500 (class 0 OID 0)
-- Dependencies: 222
-- Name: seq_conta_pagar; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_conta_pagar', 1, false);


--
-- TOC entry 3501 (class 0 OID 0)
-- Dependencies: 219
-- Name: seq_conta_receber; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_conta_receber', 1, false);


--
-- TOC entry 3502 (class 0 OID 0)
-- Dependencies: 224
-- Name: seq_cupom_desconto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_cupom_desconto', 1, false);


--
-- TOC entry 3503 (class 0 OID 0)
-- Dependencies: 215
-- Name: seq_endereco; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_endereco', 1, false);


--
-- TOC entry 3504 (class 0 OID 0)
-- Dependencies: 220
-- Name: seq_forma_pagamento; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);


--
-- TOC entry 3505 (class 0 OID 0)
-- Dependencies: 226
-- Name: seq_imagem_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);


--
-- TOC entry 3506 (class 0 OID 0)
-- Dependencies: 235
-- Name: seq_item_venda_loja; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);


--
-- TOC entry 3507 (class 0 OID 0)
-- Dependencies: 209
-- Name: seq_marca_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);


--
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 227
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);


--
-- TOC entry 3509 (class 0 OID 0)
-- Dependencies: 232
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);


--
-- TOC entry 3510 (class 0 OID 0)
-- Dependencies: 229
-- Name: seq_nota_item_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);


--
-- TOC entry 3511 (class 0 OID 0)
-- Dependencies: 216
-- Name: seq_pessoa; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_pessoa', 1, false);


--
-- TOC entry 3512 (class 0 OID 0)
-- Dependencies: 225
-- Name: seq_produto; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_produto', 1, false);


--
-- TOC entry 3513 (class 0 OID 0)
-- Dependencies: 231
-- Name: seq_status_rastreio; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);


--
-- TOC entry 3514 (class 0 OID 0)
-- Dependencies: 217
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_usuario', 1, false);


--
-- TOC entry 3515 (class 0 OID 0)
-- Dependencies: 233
-- Name: seq_venda_compra_loja_virtual; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.seq_venda_compra_loja_virtual', 1, false);


--
-- TOC entry 3237 (class 2606 OID 123179)
-- Name: acesso acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acesso
    ADD CONSTRAINT acesso_pkey PRIMARY KEY (id);


--
-- TOC entry 3255 (class 2606 OID 123408)
-- Name: avaliacao_produto avaliacao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT avaliacao_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3239 (class 2606 OID 123184)
-- Name: categoria_produto categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 123261)
-- Name: conta_pagar conta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT conta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 123245)
-- Name: conta_receber conta_receber_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT conta_receber_pkey PRIMARY KEY (id);


--
-- TOC entry 3247 (class 2606 OID 123267)
-- Name: cupom_desconto cupom_desconto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cupom_desconto
    ADD CONSTRAINT cupom_desconto_pkey PRIMARY KEY (id);


--
-- TOC entry 3257 (class 2606 OID 131360)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 131387)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 3259 (class 2606 OID 131377)
-- Name: imagem_produto imagem_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT imagem_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3253 (class 2606 OID 123381)
-- Name: item_venda_loja item_venda_loja_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT item_venda_loja_pkey PRIMARY KEY (id);


--
-- TOC entry 3241 (class 2606 OID 123196)
-- Name: marca_produto marca_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT marca_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3263 (class 2606 OID 131399)
-- Name: nota_fiscal_compra nota_fiscal_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT nota_fiscal_compra_pkey PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 131416)
-- Name: nota_fiscal_venda nota_fiscal_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 123308)
-- Name: nota_item_produto nota_item_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_item_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3267 (class 2606 OID 131433)
-- Name: pessoa_fisica pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id);


--
-- TOC entry 3269 (class 2606 OID 131440)
-- Name: pessoa_juridica pessoa_juridica_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 131447)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 123326)
-- Name: status_rastreio status_rastreio_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT status_rastreio_pkey PRIMARY KEY (id);


--
-- TOC entry 3275 (class 2606 OID 131484)
-- Name: usuario_acesso uk_fhwpg5wu1u5p306q8gycxn9ky; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT uk_fhwpg5wu1u5p306q8gycxn9ky UNIQUE (acesso_id);


--
-- TOC entry 3277 (class 2606 OID 131486)
-- Name: usuario_acesso unique_acesso_user; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);


--
-- TOC entry 3273 (class 2606 OID 131474)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3279 (class 2606 OID 131482)
-- Name: venda_compra_loja_virtual venda_compra_loja_virtual_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT venda_compra_loja_virtual_pkey PRIMARY KEY (id);


--
-- TOC entry 3303 (class 2620 OID 131541)
-- Name: avaliacao_produto validachavepessoaavaliacaoprodutoinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoaavaliacaoprodutoinsert BEFORE INSERT ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3302 (class 2620 OID 131542)
-- Name: avaliacao_produto validachavepessoaavaliacaoprodutoupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoaavaliacaoprodutoupdate BEFORE UPDATE ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3301 (class 2620 OID 131543)
-- Name: conta_pagar validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3300 (class 2620 OID 131544)
-- Name: conta_pagar validachavepessoacontapagarupdateinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoacontapagarupdateinsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3297 (class 2620 OID 131545)
-- Name: conta_receber validachavepessoacontareceberupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoacontareceberupdate BEFORE UPDATE ON public.conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3296 (class 2620 OID 131546)
-- Name: conta_receber validachavepessoacontareceberupdateinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoacontareceberupdateinsert BEFORE INSERT ON public.conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3305 (class 2620 OID 131547)
-- Name: endereco validachavepessoaenderecoupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoaenderecoupdate BEFORE UPDATE ON public.endereco FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3304 (class 2620 OID 131548)
-- Name: endereco validachavepessoaenderecoupdateinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoaenderecoupdateinsert BEFORE INSERT ON public.endereco FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3298 (class 2620 OID 131555)
-- Name: conta_pagar validachavepessoafornecedorcontapagarinsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoafornecedorcontapagarinsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoafornecedor();


--
-- TOC entry 3299 (class 2620 OID 131554)
-- Name: conta_pagar validachavepessoafornecedorcontapagarupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoafornecedorcontapagarupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoafornecedor();


--
-- TOC entry 3308 (class 2620 OID 131557)
-- Name: usuario validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3311 (class 2620 OID 131559)
-- Name: venda_compra_loja_virtual validachavepessoainsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3306 (class 2620 OID 131550)
-- Name: nota_fiscal_compra validachavepessoanotafiscalcomprainsert; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoanotafiscalcomprainsert BEFORE INSERT ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3307 (class 2620 OID 131549)
-- Name: nota_fiscal_compra validachavepessoanotafiscalcompraupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoanotafiscalcompraupdate BEFORE UPDATE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3309 (class 2620 OID 131556)
-- Name: usuario validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3310 (class 2620 OID 131558)
-- Name: venda_compra_loja_virtual validachavepessoaupdate; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.venda_compra_loja_virtual FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


--
-- TOC entry 3289 (class 2606 OID 131502)
-- Name: usuario_acesso acesso_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id);


--
-- TOC entry 3287 (class 2606 OID 131400)
-- Name: nota_fiscal_compra conta_pagar_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT conta_pagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.conta_pagar(id);


--
-- TOC entry 3291 (class 2606 OID 131512)
-- Name: venda_compra_loja_virtual cupom_desconto_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT cupom_desconto_fk FOREIGN KEY (cupom_desconto_id) REFERENCES public.cupom_desconto(id);


--
-- TOC entry 3292 (class 2606 OID 131517)
-- Name: venda_compra_loja_virtual endereco_cobranca_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_cobranca_fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.endereco(id);


--
-- TOC entry 3293 (class 2606 OID 131522)
-- Name: venda_compra_loja_virtual endereco_entrega_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT endereco_entrega_fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.endereco(id);


--
-- TOC entry 3294 (class 2606 OID 131527)
-- Name: venda_compra_loja_virtual forma_pagamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT forma_pagamento_fk FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);


--
-- TOC entry 3280 (class 2606 OID 131405)
-- Name: nota_item_produto nota_fiscal_compra_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_fiscal_compra_id_fk FOREIGN KEY (nota_fiscal_compra_id) REFERENCES public.nota_fiscal_compra(id);


--
-- TOC entry 3295 (class 2606 OID 131532)
-- Name: venda_compra_loja_virtual nota_fiscal_venda_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda_compra_loja_virtual
    ADD CONSTRAINT nota_fiscal_venda_fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.nota_fiscal_venda(id);


--
-- TOC entry 3285 (class 2606 OID 131448)
-- Name: avaliacao_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3286 (class 2606 OID 131453)
-- Name: imagem_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3283 (class 2606 OID 131458)
-- Name: item_venda_loja produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3281 (class 2606 OID 131463)
-- Name: nota_item_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 3290 (class 2606 OID 131507)
-- Name: usuario_acesso usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3284 (class 2606 OID 131487)
-- Name: item_venda_loja venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 3288 (class 2606 OID 131492)
-- Name: nota_fiscal_venda venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


--
-- TOC entry 3282 (class 2606 OID 131497)
-- Name: status_rastreio venda_compra_loja_virtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT venda_compra_loja_virtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.venda_compra_loja_virtual(id);


-- Completed on 2022-10-08 18:34:20

--
-- PostgreSQL database dump complete
--

