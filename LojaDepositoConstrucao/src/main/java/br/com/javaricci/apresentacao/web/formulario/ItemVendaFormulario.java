package br.com.javaricci.apresentacao.web.formulario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Produto e quantidade informados no registro de venda.
 */
public class ItemVendaFormulario {

    @NotNull(message = "Selecione o produto de cada item da venda.")
    private Integer idProduto;

    @NotNull(message = "Informe a quantidade de cada item da venda.")
    @Min(value = 1, message = "A quantidade de cada item deve ser de pelo menos 1.")
    private Integer quantidade;

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
