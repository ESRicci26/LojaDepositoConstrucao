package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.aplicacao.modelo.ClienteCompleto;

import java.util.List;

/**
 * Caso de uso: gerenciamento de clientes.
 */
public interface ClienteCasoUso {

    Integer inserir(ClienteCompleto clienteCompleto);

    void atualizar(ClienteCompleto clienteCompleto);

    void deletar(Integer idCliente);

    List<ClienteCompleto> listar();

    ClienteCompleto buscarPorId(Integer idCliente);
}
