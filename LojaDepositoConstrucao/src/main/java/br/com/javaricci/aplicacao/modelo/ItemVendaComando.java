package br.com.javaricci.aplicacao.modelo;

/**
 * Produto e quantidade solicitados em uma nova venda.
 */
public record ItemVendaComando(Integer idProduto, int quantidade) {
}
