-- =====================================================================
-- Criação do banco de dados LojaDepositoConstrucao (PostgreSQL)
-- Execute este script conectado ao banco "postgres" (ou outro banco
-- administrativo), pois o CREATE DATABASE não pode rodar dentro de uma
-- transação nem no próprio banco que será criado.
--
--   psql -U postgres -f banco/01_criar_banco.sql
-- =====================================================================

CREATE DATABASE lojadepositoconstrucao
    WITH ENCODING = 'UTF8'
    TEMPLATE = template0;
