package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.porta.entrada.ProdutoCasoUso;
import br.com.javaricci.aplicacao.porta.saida.LojaRepositorio;
import br.com.javaricci.aplicacao.porta.saida.ProdutoRepositorio;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negócio do cadastro de produtos.
 */
@Service
public class ProdutoServico implements ProdutoCasoUso {

    private final ProdutoRepositorio produtoRepositorio;
    private final LojaRepositorio lojaRepositorio;
    private final Integer idLojaPadrao;

    public ProdutoServico(ProdutoRepositorio produtoRepositorio,
                          LojaRepositorio lojaRepositorio,
                          @Value("${loja.id-padrao}") Integer idLojaPadrao) {
        this.produtoRepositorio = produtoRepositorio;
        this.lojaRepositorio = lojaRepositorio;
        this.idLojaPadrao = idLojaPadrao;
    }

    @Override
    @Transactional
    public Integer inserir(Produto produto) {
        produto.setIdLoja(idLojaPadrao);
        if (!lojaRepositorio.existePorId(produto.getIdLoja())) {
            throw new RegraNegocioException("A loja com o código " + produto.getIdLoja() + " não existe.");
        }
        return produtoRepositorio.inserir(produto);
    }

    @Override
    @Transactional
    public void atualizar(Produto produto) {
        Produto existente = buscarPorId(produto.getId());
        produto.setIdLoja(existente.getIdLoja());
        produtoRepositorio.atualizar(produto);
    }

    @Override
    @Transactional
    public void deletar(Integer idProduto) {
        buscarPorId(idProduto);
        if (produtoRepositorio.existeEmVenda(idProduto)) {
            throw new RegraNegocioException(
                    "O produto não pode ser excluído porque está associado a uma venda.");
        }
        produtoRepositorio.deletar(idProduto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produto> listar() {
        return produtoRepositorio.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Produto buscarPorId(Integer idProduto) {
        return produtoRepositorio.buscarPorId(idProduto)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));
    }
}
