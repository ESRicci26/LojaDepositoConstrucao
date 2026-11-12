package br.com.javaricci.aplicacao.porta.entrada;

/**
 * Caso de uso: autenticação de administradores.
 */
public interface AutenticacaoCasoUso {

    boolean autenticar(String login, String senha);
}
