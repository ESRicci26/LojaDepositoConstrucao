package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.aplicacao.modelo.DetalheVenda;
import br.com.javaricci.aplicacao.modelo.RegistrarVendaComando;
import br.com.javaricci.aplicacao.modelo.ResumoVenda;

import java.time.LocalDate;
import java.util.List;

/**
 * Caso de uso: registro e consulta de vendas.
 */
public interface VendaCasoUso {

    Integer inserir(RegistrarVendaComando comando);

    void atualizarPagamento(Integer idVenda, LocalDate dataPagamento);

    void deletar(Integer idVenda);

    List<ResumoVenda> listar();

    DetalheVenda detalhar(Integer idVenda);
}
