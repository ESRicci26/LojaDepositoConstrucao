package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.TelefoneCliente;

import java.util.List;

/**
 * Porta de saída para persistência de telefones de clientes.
 */
public interface TelefoneClienteRepositorio {

    Integer inserir(TelefoneCliente telefone);

    void deletarPorIdCliente(Integer idCliente);

    List<TelefoneCliente> listar();

    List<TelefoneCliente> listarPorIdCliente(Integer idCliente);
}
