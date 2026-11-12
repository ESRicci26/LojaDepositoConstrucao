package br.com.javaricci.apresentacao.web.formulario;

import br.com.javaricci.aplicacao.modelo.ClienteCompleto;
import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import br.com.javaricci.dominio.modelo.TelefoneCliente;
import br.com.javaricci.dominio.modelo.TipoCliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dados do formulário de cadastro e edição de cliente.
 */
public class ClienteFormulario {

    private Integer id;

    @NotBlank(message = "Informe o nome do cliente.")
    @Size(max = 155, message = "O nome deve ter no máximo 155 caracteres.")
    private String nome;

    @NotNull(message = "Informe o RG (somente números).")
    @Positive(message = "O RG deve ser um número positivo.")
    private Long rg;

    @NotBlank(message = "Informe o CPF.")
    @Pattern(regexp = "^\\d{11}$", message = "O CPF deve ter 11 dígitos numéricos.")
    private String cpf;

    @NotNull(message = "Selecione o tipo do cliente.")
    private TipoCliente tipo;

    private List<@Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.") String> telefones =
            new ArrayList<>();

    @Valid
    private List<EnderecoFormulario> enderecos = new ArrayList<>();

    public static ClienteFormulario de(ClienteCompleto clienteCompleto) {
        Cliente cliente = clienteCompleto.cliente();
        ClienteFormulario formulario = new ClienteFormulario();
        formulario.setId(cliente.getId());
        formulario.setNome(cliente.getNome());
        formulario.setRg(cliente.getRg());
        formulario.setCpf(cliente.getCpf());
        formulario.setTipo(cliente.getTipo());
        formulario.setTelefones(clienteCompleto.telefones().stream()
                .map(TelefoneCliente::getNumero)
                .collect(Collectors.toCollection(ArrayList::new)));
        formulario.setEnderecos(clienteCompleto.enderecos().stream()
                .map(EnderecoFormulario::de)
                .collect(Collectors.toCollection(ArrayList::new)));
        return formulario;
    }

    public ClienteCompleto paraClienteCompleto() {
        Cliente cliente = new Cliente(id, nome.trim(), rg, cpf, tipo);

        List<TelefoneCliente> listaTelefones = telefones.stream()
                .filter(numero -> numero != null && !numero.isBlank())
                .map(numero -> new TelefoneCliente(null, null, numero.trim()))
                .toList();

        List<EnderecoCliente> listaEnderecos = enderecos.stream()
                .map(EnderecoFormulario::paraEntidade)
                .toList();

        return new ClienteCompleto(cliente, listaTelefones, listaEnderecos);
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

    public Long getRg() {
        return rg;
    }

    public void setRg(Long rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public List<EnderecoFormulario> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoFormulario> enderecos) {
        this.enderecos = enderecos;
    }
}
