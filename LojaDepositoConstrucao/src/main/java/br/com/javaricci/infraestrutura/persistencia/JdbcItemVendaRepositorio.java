package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.ItemVendaRepositorio;
import br.com.javaricci.dominio.modelo.ItemVenda;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcItemVendaRepositorio implements ItemVendaRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO tb_produto_venda (id_venda, cd_produto, qt_produto_retirado, vl_total_produto) "
                    + "VALUES (?, ?, ?, ?) RETURNING id_produto_venda";

    private static final String SQL_DELETAR_POR_VENDA =
            "DELETE FROM tb_produto_venda WHERE id_venda = ?";

    private static final String SQL_LISTAR_POR_VENDA =
            "SELECT id_produto_venda, id_venda, cd_produto, qt_produto_retirado, vl_total_produto "
                    + "FROM tb_produto_venda WHERE id_venda = ? ORDER BY id_produto_venda";

    private static final RowMapper<ItemVenda> MAPEADOR = (rs, linha) -> new ItemVenda(
            rs.getInt("id_produto_venda"),
            rs.getInt("id_venda"),
            rs.getInt("cd_produto"),
            rs.getInt("qt_produto_retirado"),
            rs.getBigDecimal("vl_total_produto"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemVendaRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer inserir(ItemVenda item) {
        try {
            return jdbcTemplate.queryForObject(SQL_INSERIR, Integer.class,
                    item.getIdVenda(), item.getCodigoProduto(), item.getQuantidade(), item.getValorTotal());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void deletarPorIdVenda(Integer idVenda) {
        jdbcTemplate.update(SQL_DELETAR_POR_VENDA, idVenda);
    }

    @Override
    public List<ItemVenda> listarPorIdVenda(Integer idVenda) {
        return jdbcTemplate.query(SQL_LISTAR_POR_VENDA, MAPEADOR, idVenda);
    }
}
