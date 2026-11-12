package br.com.javaricci.aplicacao.porta.saida;

/**
 * Porta de saída para verificação de senhas criptografadas.
 */
public interface CodificadorSenha {

    String codificar(String senhaPura);

    boolean confere(String senhaPura, String senhaCriptografada);
}
