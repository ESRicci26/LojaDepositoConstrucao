package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.ItemVenda;

import java.util.List;

/**
 * Porta de saída para persistência dos itens de venda.
 */
public interface ItemVendaRepositorio {

    Integer inserir(ItemVenda item);

    void deletarPorIdVenda(Integer idVenda);

    List<ItemVenda> listarPorIdVenda(Integer idVenda);
}
