package br.com.javaricci.apresentacao.web.formulario;

import br.com.javaricci.dominio.modelo.EnderecoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Endereço informado no cadastro de cliente.
 */
public class EnderecoFormulario {

    @NotBlank(message = "Informe o endereço.")
    @Size(max = 150, message = "O endereço deve ter no máximo 150 caracteres.")
    private String nomeEndereco;

    @NotBlank(message = "Informe a cidade.")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres.")
    private String nomeCidade;

    @NotBlank(message = "Informe a UF.")
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "A UF deve ter 2 letras.")
    private String siglaUf;

    public static EnderecoFormulario de(EnderecoCliente endereco) {
        EnderecoFormulario formulario = new EnderecoFormulario();
        formulario.setNomeEndereco(endereco.getNomeEndereco());
        formulario.setNomeCidade(endereco.getNomeCidade());
        formulario.setSiglaUf(endereco.getSiglaUf());
        return formulario;
    }

    public EnderecoCliente paraEntidade() {
        return new EnderecoCliente(null, null,
                siglaUf.trim().toUpperCase(), nomeEndereco.trim(), nomeCidade.trim());
    }

    public String getNomeEndereco() {
        return nomeEndereco;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }
}
