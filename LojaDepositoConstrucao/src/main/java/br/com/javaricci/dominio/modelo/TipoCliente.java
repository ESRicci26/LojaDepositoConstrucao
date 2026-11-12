package br.com.javaricci.dominio.modelo;

/**
 * Classificação do cliente conforme o relacionamento com a loja.
 */
public enum TipoCliente {

    NOVO("Novo"),
    ANTIGO("Antigo");

    private final String descricao;

    TipoCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
