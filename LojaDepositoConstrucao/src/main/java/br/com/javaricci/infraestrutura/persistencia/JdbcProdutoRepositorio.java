package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.ProdutoRepositorio;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Produto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProdutoRepositorio implements ProdutoRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO tb_produto (id_loja, no_produto, qt_produto_estoque, vl_produto_unitario) "
                    + "VALUES (?, ?, ?, ?) RETURNING cd_produto";

    private static final String SQL_ATUALIZAR =
            "UPDATE tb_produto SET id_loja = ?, no_produto = ?, qt_produto_estoque = ?, "
                    + "vl_produto_unitario = ? WHERE cd_produto = ?";

    private static final String SQL_DELETAR = "DELETE FROM tb_produto WHERE cd_produto = ?";

    private static final String SQL_LISTAR =
            "SELECT cd_produto, id_loja, no_produto, qt_produto_estoque, vl_produto_unitario "
                    + "FROM tb_produto ORDER BY cd_produto";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT cd_produto, id_loja, no_produto, qt_produto_estoque, vl_produto_unitario "
                    + "FROM tb_produto WHERE cd_produto = ?";

    private static final String SQL_EXISTE_EM_VENDA =
            "SELECT EXISTS (SELECT 1 FROM tb_produto_venda WHERE cd_produto = ?)";

    // a condição "qt_produto_estoque >= ?" garante a baixa atômica, sem estoque negativo
    private static final String SQL_BAIXAR_ESTOQUE =
            "UPDATE tb_produto SET qt_produto_estoque = qt_produto_estoque - ? "
                    + "WHERE cd_produto = ? AND qt_produto_estoque >= ?";

    private static final String SQL_DEVOLVER_ESTOQUE =
            "UPDATE tb_produto SET qt_produto_estoque = qt_produto_estoque + ? WHERE cd_produto = ?";

    private static final RowMapper<Produto> MAPEADOR = (rs, linha) -> new Produto(
            rs.getInt("cd_produto"),
            rs.getInt("id_loja"),
            rs.getString("no_produto"),
            rs.getInt("qt_produto_estoque"),
            rs.getBigDecimal("vl_produto_unitario"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcProdutoRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer inserir(Produto produto) {
        try {
            return jdbcTemplate.queryForObject(SQL_INSERIR, Integer.class,
                    produto.getIdLoja(), produto.getNome(),
                    produto.getQuantidadeEstoque(), produto.getValorUnitario());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void atualizar(Produto produto) {
        try {
            jdbcTemplate.update(SQL_ATUALIZAR,
                    produto.getIdLoja(), produto.getNome(), produto.getQuantidadeEstoque(),
                    produto.getValorUnitario(), produto.getId());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void deletar(Integer idProduto) {
        try {
            jdbcTemplate.update(SQL_DELETAR, idProduto);
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public List<Produto> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public Optional<Produto> buscarPorId(Integer idProduto) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR, idProduto).stream().findFirst();
    }

    @Override
    public boolean existeEmVenda(Integer idProduto) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SQL_EXISTE_EM_VENDA, Boolean.class, idProduto));
    }

    @Override
    public boolean baixarEstoque(Integer idProduto, int quantidade) {
        if (quantidade <= 0) {
            throw new RegraNegocioException("A quantidade deve ser maior que zero.");
        }
        return jdbcTemplate.update(SQL_BAIXAR_ESTOQUE, quantidade, idProduto, quantidade) > 0;
    }

    @Override
    public void devolverEstoque(Integer idProduto, int quantidade) {
        jdbcTemplate.update(SQL_DEVOLVER_ESTOQUE, quantidade, idProduto);
    }
}
