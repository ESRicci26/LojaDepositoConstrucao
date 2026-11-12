package br.com.javaricci.aplicacao.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Dados necessários para registrar uma venda. O valor total é sempre calculado pelo sistema.
 */
public record RegistrarVendaComando(Integer idCliente,
                                    LocalDate dataPagamento,
                                    List<ItemVendaComando> itens) {
}
