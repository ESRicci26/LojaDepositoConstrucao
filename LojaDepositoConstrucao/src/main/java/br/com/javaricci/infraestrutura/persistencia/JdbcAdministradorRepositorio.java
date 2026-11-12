package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.AdministradorRepositorio;
import br.com.javaricci.dominio.modelo.Administrador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcAdministradorRepositorio implements AdministradorRepositorio {

    private static final String SQL_BUSCAR_POR_LOGIN =
            "SELECT no_login, no_senha FROM tb_administrador WHERE no_login = ?";

    private static final RowMapper<Administrador> MAPEADOR = (rs, linha) ->
            new Administrador(rs.getString("no_login"), rs.getString("no_senha"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcAdministradorRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Administrador> buscarPorLogin(String login) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_LOGIN, MAPEADOR, login).stream().findFirst();
    }
}
