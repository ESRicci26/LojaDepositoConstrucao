package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.ClienteRepositorio;
import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.TipoCliente;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClienteRepositorio implements ClienteRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO tb_cliente (no_cliente, nr_rg, nr_cpf, tp_cliente) "
                    + "VALUES (?, ?, ?, ?) RETURNING id_cliente";

    private static final String SQL_ATUALIZAR =
            "UPDATE tb_cliente SET no_cliente = ?, nr_rg = ?, nr_cpf = ?, tp_cliente = ? "
                    + "WHERE id_cliente = ?";

    private static final String SQL_DELETAR = "DELETE FROM tb_cliente WHERE id_cliente = ?";

    private static final String SQL_LISTAR =
            "SELECT id_cliente, no_cliente, nr_rg, nr_cpf, tp_cliente FROM tb_cliente ORDER BY id_cliente";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id_cliente, no_cliente, nr_rg, nr_cpf, tp_cliente FROM tb_cliente WHERE id_cliente = ?";

    private static final RowMapper<Cliente> MAPEADOR = (rs, linha) -> new Cliente(
            rs.getInt("id_cliente"),
            rs.getString("no_cliente"),
            rs.getLong("nr_rg"),
            rs.getString("nr_cpf"),
            TipoCliente.valueOf(rs.getString("tp_cliente")));

    private final JdbcTemplate jdbcTemplate;

    public JdbcClienteRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer inserir(Cliente cliente) {
        try {
            return jdbcTemplate.queryForObject(SQL_INSERIR, Integer.class,
                    cliente.getNome(), cliente.getRg(), cliente.getCpf(), cliente.getTipo().name());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        try {
            jdbcTemplate.update(SQL_ATUALIZAR,
                    cliente.getNome(), cliente.getRg(), cliente.getCpf(), cliente.getTipo().name(),
                    cliente.getId());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void deletar(Integer idCliente) {
        try {
            jdbcTemplate.update(SQL_DELETAR, idCliente);
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public List<Cliente> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public Optional<Cliente> buscarPorId(Integer idCliente) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR, idCliente).stream().findFirst();
    }
}
