package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Produto;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de produtos e controle de estoque.
 */
public interface ProdutoRepositorio {

    Integer inserir(Produto produto);

    void atualizar(Produto produto);

    void deletar(Integer idProduto);

    List<Produto> listar();

    Optional<Produto> buscarPorId(Integer idProduto);

    boolean existeEmVenda(Integer idProduto);

    /**
     * Baixa o estoque de forma atômica.
     *
     * @return false quando o estoque disponível é insuficiente (nada é alterado)
     */
    boolean baixarEstoque(Integer idProduto, int quantidade);

    void devolverEstoque(Integer idProduto, int quantidade);
}
