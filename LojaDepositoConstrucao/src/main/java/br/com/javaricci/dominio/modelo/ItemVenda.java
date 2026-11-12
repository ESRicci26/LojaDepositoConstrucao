package br.com.javaricci.dominio.modelo;

import java.math.BigDecimal;

/**
 * Produto retirado em uma venda, com a quantidade e o valor total do item.
 */
public class ItemVenda {

    private Integer id;
    private Integer idVenda;
    private Integer codigoProduto;
    private int quantidade;
    private BigDecimal valorTotal;

    public ItemVenda() {
        this.valorTotal = BigDecimal.ZERO;
    }

    public ItemVenda(Integer id, Integer idVenda, Integer codigoProduto, int quantidade, BigDecimal valorTotal) {
        this.id = id;
        this.idVenda = idVenda;
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
