package br.com.javaricci.dominio.excecao;

/**
 * Violação de uma regra de negócio. A mensagem é adequada para exibição ao usuário.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }

    public RegraNegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
