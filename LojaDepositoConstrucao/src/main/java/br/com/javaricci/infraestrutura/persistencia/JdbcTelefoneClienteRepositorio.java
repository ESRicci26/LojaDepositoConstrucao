package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.TelefoneClienteRepositorio;
import br.com.javaricci.dominio.modelo.TelefoneCliente;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTelefoneClienteRepositorio implements TelefoneClienteRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO tb_telefone_cliente (id_cliente, nr_telefone) "
                    + "VALUES (?, ?) RETURNING id_telefone_cliente";

    private static final String SQL_DELETAR_POR_CLIENTE =
            "DELETE FROM tb_telefone_cliente WHERE id_cliente = ?";

    private static final String SQL_LISTAR =
            "SELECT id_telefone_cliente, id_cliente, nr_telefone "
                    + "FROM tb_telefone_cliente ORDER BY id_telefone_cliente";

    private static final String SQL_LISTAR_POR_CLIENTE =
            "SELECT id_telefone_cliente, id_cliente, nr_telefone "
                    + "FROM tb_telefone_cliente WHERE id_cliente = ? ORDER BY id_telefone_cliente";

    private static final RowMapper<TelefoneCliente> MAPEADOR = (rs, linha) -> new TelefoneCliente(
            rs.getInt("id_telefone_cliente"),
            rs.getInt("id_cliente"),
            rs.getString("nr_telefone"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcTelefoneClienteRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer inserir(TelefoneCliente telefone) {
        try {
            return jdbcTemplate.queryForObject(SQL_INSERIR, Integer.class,
                    telefone.getIdCliente(), telefone.getNumero());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void deletarPorIdCliente(Integer idCliente) {
        jdbcTemplate.update(SQL_DELETAR_POR_CLIENTE, idCliente);
    }

    @Override
    public List<TelefoneCliente> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public List<TelefoneCliente> listarPorIdCliente(Integer idCliente) {
        return jdbcTemplate.query(SQL_LISTAR_POR_CLIENTE, MAPEADOR, idCliente);
    }
}
