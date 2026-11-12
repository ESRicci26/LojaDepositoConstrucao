package br.com.javaricci.dominio.modelo;

import java.math.BigDecimal;

/**
 * Produto disponível para venda, com controle de estoque.
 */
public class Produto {

    private Integer id;
    private Integer idLoja;
    private String nome;
    private int quantidadeEstoque;
    private BigDecimal valorUnitario;

    public Produto() {
        this.valorUnitario = BigDecimal.ZERO;
    }

    public Produto(Integer id, Integer idLoja, String nome, int quantidadeEstoque, BigDecimal valorUnitario) {
        this.id = id;
        this.idLoja = idLoja;
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valorUnitario = valorUnitario;
    }

    /**
     * Calcula o valor total para a quantidade informada.
     */
    public BigDecimal calcularValorTotal(int quantidade) {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(Integer idLoja) {
        this.idLoja = idLoja;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
