package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.LojaRepositorio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLojaRepositorio implements LojaRepositorio {

    private static final String SQL_EXISTE_POR_ID =
            "SELECT EXISTS (SELECT 1 FROM tb_loja WHERE id_loja = ?)";

    private final JdbcTemplate jdbcTemplate;

    public JdbcLojaRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existePorId(Integer idLoja) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SQL_EXISTE_POR_ID, Boolean.class, idLoja));
    }
}
