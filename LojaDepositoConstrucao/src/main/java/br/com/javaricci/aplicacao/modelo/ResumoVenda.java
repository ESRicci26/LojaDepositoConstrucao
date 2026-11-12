package br.com.javaricci.aplicacao.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Linha da listagem de vendas.
 */
public record ResumoVenda(Integer id,
                          String nomeCliente,
                          LocalDate dataPagamento,
                          BigDecimal valorTotal) {

    public boolean isPaga() {
        return dataPagamento != null;
    }
}
