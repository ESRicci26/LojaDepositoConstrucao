package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Venda;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de vendas.
 */
public interface VendaRepositorio {

    Integer inserir(Venda venda);

    void atualizar(Venda venda);

    void deletar(Integer idVenda);

    List<Venda> listar();

    Optional<Venda> buscarPorId(Integer idVenda);

    boolean existePorIdCliente(Integer idCliente);
}
