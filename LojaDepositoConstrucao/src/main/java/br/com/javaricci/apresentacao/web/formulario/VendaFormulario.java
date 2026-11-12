package br.com.javaricci.apresentacao.web.formulario;

import br.com.javaricci.aplicacao.modelo.ItemVendaComando;
import br.com.javaricci.aplicacao.modelo.RegistrarVendaComando;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Dados do formulário de registro de venda.
 */
public class VendaFormulario {

    @NotNull(message = "Selecione o cliente.")
    private Integer idCliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;

    @Valid
    @NotEmpty(message = "Adicione ao menos um produto à venda.")
    private List<ItemVendaFormulario> itens = new ArrayList<>();

    public RegistrarVendaComando paraComando() {
        List<ItemVendaComando> itensComando = itens.stream()
                .map(item -> new ItemVendaComando(item.getIdProduto(), item.getQuantidade()))
                .toList();
        return new RegistrarVendaComando(idCliente, dataPagamento, itensComando);
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

    public List<ItemVendaFormulario> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaFormulario> itens) {
        this.itens = itens;
    }
}
