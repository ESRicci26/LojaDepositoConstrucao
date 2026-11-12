package br.com.javaricci.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Venda realizada para um cliente. A venda é considerada paga quando
 * possui data de pagamento.
 */
public class Venda {

    private Integer id;
    private Integer idCliente;
    private LocalDate dataPagamento;
    private BigDecimal valorTotal;

    public Venda() {
        this.valorTotal = BigDecimal.ZERO;
    }

    public Venda(Integer id, Integer idCliente, LocalDate dataPagamento, BigDecimal valorTotal) {
        this.id = id;
        this.idCliente = idCliente;
        this.dataPagamento = dataPagamento;
        this.valorTotal = valorTotal;
    }

    public boolean isPaga() {
        return dataPagamento != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
