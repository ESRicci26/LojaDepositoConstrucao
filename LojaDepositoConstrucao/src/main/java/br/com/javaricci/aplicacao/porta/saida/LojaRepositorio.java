package br.com.javaricci.aplicacao.porta.saida;

/**
 * Porta de saída para consulta de lojas.
 */
public interface LojaRepositorio {

    boolean existePorId(Integer idLoja);
}
