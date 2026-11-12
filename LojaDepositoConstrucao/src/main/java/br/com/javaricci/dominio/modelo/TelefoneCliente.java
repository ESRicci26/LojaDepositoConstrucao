package br.com.javaricci.dominio.modelo;

/**
 * Telefone de contato de um cliente.
 */
public class TelefoneCliente {

    private Integer id;
    private Integer idCliente;
    private String numero;

    public TelefoneCliente() {
    }

    public TelefoneCliente(Integer id, Integer idCliente, String numero) {
        this.id = id;
        this.idCliente = idCliente;
        this.numero = numero;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
