package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.VendaRepositorio;
import br.com.javaricci.dominio.modelo.Venda;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcVendaRepositorio implements VendaRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO tb_venda (id_cliente, dh_pagamento, vl_total_venda, ic_pago) "
                    + "VALUES (?, ?, ?, ?) RETURNING id_venda";

    private static final String SQL_ATUALIZAR =
            "UPDATE tb_venda SET id_cliente = ?, dh_pagamento = ?, vl_total_venda = ?, ic_pago = ? "
                    + "WHERE id_venda = ?";

    private static final String SQL_DELETAR = "DELETE FROM tb_venda WHERE id_venda = ?";

    private static final String SQL_LISTAR =
            "SELECT id_venda, id_cliente, dh_pagamento, vl_total_venda FROM tb_venda ORDER BY id_venda";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id_venda, id_cliente, dh_pagamento, vl_total_venda FROM tb_venda WHERE id_venda = ?";

    private static final String SQL_EXISTE_POR_CLIENTE =
            "SELECT EXISTS (SELECT 1 FROM tb_venda WHERE id_cliente = ?)";

    private static final RowMapper<Venda> MAPEADOR = (rs, linha) -> new Venda(
            rs.getInt("id_venda"),
            rs.getInt("id_cliente"),
            rs.getObject("dh_pagamento", LocalDate.class),
            rs.getBigDecimal("vl_total_venda"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcVendaRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer inserir(Venda venda) {
        try {
            return jdbcTemplate.queryForObject(SQL_INSERIR, Integer.class,
                    venda.getIdCliente(), parametroData(venda.getDataPagamento()),
                    venda.getValorTotal(), venda.isPaga());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void atualizar(Venda venda) {
        try {
            jdbcTemplate.update(SQL_ATUALIZAR,
                    venda.getIdCliente(), parametroData(venda.getDataPagamento()),
                    venda.getValorTotal(), venda.isPaga(), venda.getId());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void deletar(Integer idVenda) {
        try {
            jdbcTemplate.update(SQL_DELETAR, idVenda);
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public List<Venda> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public Optional<Venda> buscarPorId(Integer idVenda) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR, idVenda).stream().findFirst();
    }

    @Override
    public boolean existePorIdCliente(Integer idCliente) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SQL_EXISTE_POR_CLIENTE, Boolean.class, idCliente));
    }

    // informa o tipo DATE explicitamente para que valores nulos sejam aceitos pelo PostgreSQL
    private SqlParameterValue parametroData(LocalDate data) {
        return new SqlParameterValue(Types.DATE, data);
    }
}
