package br.com.javaricci.aplicacao.modelo;

import java.math.BigDecimal;

/**
 * Item de venda com o nome do produto, para exibição no detalhe da venda.
 */
public record ItemVendaDetalhado(Integer codigoProduto,
                                 String nomeProduto,
                                 int quantidade,
                                 BigDecimal valorUnitario,
                                 BigDecimal valorTotal) {
}
