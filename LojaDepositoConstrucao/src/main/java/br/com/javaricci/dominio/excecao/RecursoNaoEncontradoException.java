package br.com.javaricci.dominio.excecao;

/**
 * Indica que um registro solicitado não existe.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
