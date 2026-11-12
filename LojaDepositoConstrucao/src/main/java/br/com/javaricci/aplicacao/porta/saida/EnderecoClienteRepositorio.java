package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.EnderecoCliente;

import java.util.List;

/**
 * Porta de saída para persistência de endereços de clientes.
 */
public interface EnderecoClienteRepositorio {

    Integer inserir(EnderecoCliente endereco);

    void deletarPorIdCliente(Integer idCliente);

    List<EnderecoCliente> listar();

    List<EnderecoCliente> listarPorIdCliente(Integer idCliente);
}
