package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Administrador;

import java.util.Optional;

/**
 * Porta de saída para consulta de administradores.
 */
public interface AdministradorRepositorio {

    Optional<Administrador> buscarPorLogin(String login);
}
