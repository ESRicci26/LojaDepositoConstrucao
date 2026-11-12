package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.dominio.modelo.Produto;

import java.util.List;

/**
 * Caso de uso: gerenciamento de produtos.
 */
public interface ProdutoCasoUso {

    Integer inserir(Produto produto);

    void atualizar(Produto produto);

    void deletar(Integer idProduto);

    List<Produto> listar();

    Produto buscarPorId(Integer idProduto);
}
