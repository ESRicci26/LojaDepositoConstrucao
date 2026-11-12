package br.com.javaricci.dominio.modelo;

/**
 * Endereço pertencente a um cliente.
 */
public class EnderecoCliente {

    private Integer id;
    private Integer idCliente;
    private String siglaUf;
    private String nomeEndereco;
    private String nomeCidade;

    public EnderecoCliente() {
    }

    public EnderecoCliente(Integer id, Integer idCliente, String siglaUf, String nomeEndereco, String nomeCidade) {
        this.id = id;
        this.idCliente = idCliente;
        this.siglaUf = siglaUf;
        this.nomeEndereco = nomeEndereco;
        this.nomeCidade = nomeCidade;
    }

    /**
     * Texto resumido do endereço para exibição em listas.
     */
    public String resumo() {
        return nomeEndereco + ", " + nomeCidade + " - " + siglaUf;
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

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
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
}
