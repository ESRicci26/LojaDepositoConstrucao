package br.com.javaricci.dominio.modelo;

/**
 * Cliente da loja. Telefones e endereços são mantidos em entidades próprias.
 */
public class Cliente {

    private Integer id;
    private String nome;
    private Long rg;
    private String cpf;
    private TipoCliente tipo;

    public Cliente() {
    }

    public Cliente(Integer id, String nome, Long rg, String cpf, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.tipo = tipo;
    }

    /**
     * Regra de negócio: clientes novos só podem comprar com pagamento informado.
     */
    public boolean exigePagamentoNaVenda() {
        return TipoCliente.NOVO.equals(tipo);
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
}
