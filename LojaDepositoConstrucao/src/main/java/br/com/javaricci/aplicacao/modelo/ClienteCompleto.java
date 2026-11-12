package br.com.javaricci.aplicacao.modelo;

import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import br.com.javaricci.dominio.modelo.TelefoneCliente;

import java.util.List;

/**
 * Agrupa um cliente com seus telefones e endereços.
 */
public record ClienteCompleto(Cliente cliente,
                              List<TelefoneCliente> telefones,
                              List<EnderecoCliente> enderecos) {
}
