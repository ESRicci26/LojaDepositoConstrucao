package br.com.javaricci.apresentacao.web.formulario;

import br.com.javaricci.dominio.modelo.Produto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Dados do formulário de cadastro e edição de produto.
 */
public class ProdutoFormulario {

    private Integer id;

    @NotBlank(message = "Informe o nome do produto.")
    @Size(max = 200, message = "O nome deve ter no máximo 200 caracteres.")
    private String nome;

    @NotNull(message = "Informe a quantidade em estoque.")
    @PositiveOrZero(message = "A quantidade não pode ser negativa.")
    private Integer quantidadeEstoque;

    @NotNull(message = "Informe o valor unitário.")
    @DecimalMin(value = "0.00", message = "O valor unitário não pode ser negativo.")
    private BigDecimal valorUnitario;

    public static ProdutoFormulario de(Produto produto) {
        ProdutoFormulario formulario = new ProdutoFormulario();
        formulario.setId(produto.getId());
        formulario.setNome(produto.getNome());
        formulario.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        formulario.setValorUnitario(produto.getValorUnitario());
        return formulario;
    }

    public Produto paraEntidade() {
        return new Produto(id, null, nome.trim(), quantidadeEstoque, valorUnitario);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
