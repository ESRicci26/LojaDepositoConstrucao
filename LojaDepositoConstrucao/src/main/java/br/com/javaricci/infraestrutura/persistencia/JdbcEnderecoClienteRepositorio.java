package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.EnderecoClienteRepositorio;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcEnderecoClienteRepositorio implements EnderecoClienteRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO tb_endereco_cliente (id_cliente, sg_uf, no_endereco, no_cidade) "
                    + "VALUES (?, ?, ?, ?) RETURNING id_endereco_cliente";

    private static final String SQL_DELETAR_POR_CLIENTE =
            "DELETE FROM tb_endereco_cliente WHERE id_cliente = ?";

    private static final String SQL_LISTAR =
            "SELECT id_endereco_cliente, id_cliente, sg_uf, no_endereco, no_cidade "
                    + "FROM tb_endereco_cliente ORDER BY id_endereco_cliente";

    private static final String SQL_LISTAR_POR_CLIENTE =
            "SELECT id_endereco_cliente, id_cliente, sg_uf, no_endereco, no_cidade "
                    + "FROM tb_endereco_cliente WHERE id_cliente = ? ORDER BY id_endereco_cliente";

    private static final RowMapper<EnderecoCliente> MAPEADOR = (rs, linha) -> new EnderecoCliente(
            rs.getInt("id_endereco_cliente"),
            rs.getInt("id_cliente"),
            rs.getString("sg_uf"),
            rs.getString("no_endereco"),
            rs.getString("no_cidade"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcEnderecoClienteRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer inserir(EnderecoCliente endereco) {
        try {
            return jdbcTemplate.queryForObject(SQL_INSERIR, Integer.class,
                    endereco.getIdCliente(), endereco.getSiglaUf(),
                    endereco.getNomeEndereco(), endereco.getNomeCidade());
        } catch (DataIntegrityViolationException erro) {
            throw TradutorErroPersistencia.traduzir(erro);
        }
    }

    @Override
    public void deletarPorIdCliente(Integer idCliente) {
        jdbcTemplate.update(SQL_DELETAR_POR_CLIENTE, idCliente);
    }

    @Override
    public List<EnderecoCliente> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public List<EnderecoCliente> listarPorIdCliente(Integer idCliente) {
        return jdbcTemplate.query(SQL_LISTAR_POR_CLIENTE, MAPEADOR, idCliente);
    }
}
