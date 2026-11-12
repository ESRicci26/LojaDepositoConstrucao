package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de clientes.
 */
public interface ClienteRepositorio {

    Integer inserir(Cliente cliente);

    void atualizar(Cliente cliente);

    void deletar(Integer idCliente);

    List<Cliente> listar();

    Optional<Cliente> buscarPorId(Integer idCliente);
}
