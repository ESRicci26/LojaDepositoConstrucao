package br.com.javaricci.aplicacao.modelo;

import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import br.com.javaricci.dominio.modelo.Venda;

import java.util.List;

/**
 * Visão completa de uma venda: dados da venda, do cliente e dos itens.
 * O endereço pode ser nulo caso o cliente não possua endereço cadastrado.
 */
public record DetalheVenda(Venda venda,
                           Cliente cliente,
                           EnderecoCliente endereco,
                           List<ItemVendaDetalhado> itens) {
}
