package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.modelo.ItemVendaComando;
import br.com.javaricci.aplicacao.modelo.RegistrarVendaComando;
import br.com.javaricci.aplicacao.porta.saida.ClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.EnderecoClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.ItemVendaRepositorio;
import br.com.javaricci.aplicacao.porta.saida.ProdutoRepositorio;
import br.com.javaricci.aplicacao.porta.saida.VendaRepositorio;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import br.com.javaricci.dominio.modelo.ItemVenda;
import br.com.javaricci.dominio.modelo.Produto;
import br.com.javaricci.dominio.modelo.TipoCliente;
import br.com.javaricci.dominio.modelo.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testa as regras de negócio de vendas usando repositórios em memória.
 */
class VendaServicoTest {

    private final Map<Integer, Cliente> clientes = new HashMap<>();
    private final Map<Integer, Produto> produtos = new HashMap<>();
    private final Map<Integer, Venda> vendas = new HashMap<>();
    private final List<ItemVenda> itens = new ArrayList<>();
    private VendaServico servico;

    @BeforeEach
    void preparar() {
        clientes.put(1, new Cliente(1, "João", 1L, "11111111111", TipoCliente.NOVO));
        clientes.put(2, new Cliente(2, "Maria", 2L, "22222222222", TipoCliente.ANTIGO));
        produtos.put(1, new Produto(1, 1, "Cimento 50kg", 10, new BigDecimal("30.00")));

        servico = new VendaServico(new VendaFalso(), new ItemFalso(), new ClienteFalso(),
                new EnderecoFalso(), new ProdutoFalso());
    }

    @Test
    void deveCalcularTotalEBaixarEstoque() {
        Integer id = servico.inserir(new RegistrarVendaComando(1, LocalDate.now(),
                List.of(new ItemVendaComando(1, 3))));

        assertEquals(0, new BigDecimal("90.00").compareTo(vendas.get(id).getValorTotal()));
        assertEquals(7, produtos.get(1).getQuantidadeEstoque());
    }

    @Test
    void clienteNovoDeveInformarDataDePagamento() {
        assertThrows(RegraNegocioException.class, () -> servico.inserir(
                new RegistrarVendaComando(1, null, List.of(new ItemVendaComando(1, 1)))));
    }

    @Test
    void clienteAntigoPodeComprarSemPagamento() {
        Integer id = servico.inserir(new RegistrarVendaComando(2, null, List.of(new ItemVendaComando(1, 1))));
        assertEquals(false, vendas.get(id).isPaga());
    }

    @Test
    void deveRecusarQuantidadeMaiorQueEstoque() {
        assertThrows(RegraNegocioException.class, () -> servico.inserir(
                new RegistrarVendaComando(2, null, List.of(new ItemVendaComando(1, 11)))));
    }

    @Test
    void deveDevolverEstoqueAoDeletarVenda() {
        Integer id = servico.inserir(new RegistrarVendaComando(2, null, List.of(new ItemVendaComando(1, 4))));
        servico.deletar(id);

        assertEquals(10, produtos.get(1).getQuantidadeEstoque());
        assertEquals(0, vendas.size());
    }

    // ---------- repositórios em memória ----------
    private class ClienteFalso implements ClienteRepositorio {
        public Integer inserir(Cliente c) { throw new UnsupportedOperationException(); }
        public void atualizar(Cliente c) { throw new UnsupportedOperationException(); }
        public void deletar(Integer id) { throw new UnsupportedOperationException(); }
        public List<Cliente> listar() { return new ArrayList<>(clientes.values()); }
        public Optional<Cliente> buscarPorId(Integer id) { return Optional.ofNullable(clientes.get(id)); }
    }

    private class ProdutoFalso implements ProdutoRepositorio {
        public Integer inserir(Produto p) { throw new UnsupportedOperationException(); }
        public void atualizar(Produto p) { throw new UnsupportedOperationException(); }
        public void deletar(Integer id) { throw new UnsupportedOperationException(); }
        public List<Produto> listar() { return new ArrayList<>(produtos.values()); }
        public Optional<Produto> buscarPorId(Integer id) { return Optional.ofNullable(produtos.get(id)); }
        public boolean existeEmVenda(Integer id) { return false; }
        public boolean baixarEstoque(Integer id, int qtd) {
            Produto p = produtos.get(id);
            if (p.getQuantidadeEstoque() < qtd) {
                return false;
            }
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - qtd);
            return true;
        }
        public void devolverEstoque(Integer id, int qtd) {
            Produto p = produtos.get(id);
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() + qtd);
        }
    }

    private class VendaFalso implements VendaRepositorio {
        public Integer inserir(Venda v) {
            Integer id = vendas.size() + 1;
            v.setId(id);
            vendas.put(id, v);
            return id;
        }
        public void atualizar(Venda v) { vendas.put(v.getId(), v); }
        public void deletar(Integer id) { vendas.remove(id); }
        public List<Venda> listar() { return new ArrayList<>(vendas.values()); }
        public Optional<Venda> buscarPorId(Integer id) { return Optional.ofNullable(vendas.get(id)); }
        public boolean existePorIdCliente(Integer id) { return false; }
    }

    private class ItemFalso implements ItemVendaRepositorio {
        public Integer inserir(ItemVenda i) { itens.add(i); return itens.size(); }
        public void deletarPorIdVenda(Integer id) { itens.removeIf(i -> i.getIdVenda().equals(id)); }
        public List<ItemVenda> listarPorIdVenda(Integer id) {
            return itens.stream().filter(i -> i.getIdVenda().equals(id)).toList();
        }
    }

    private static class EnderecoFalso implements EnderecoClienteRepositorio {
        public Integer inserir(EnderecoCliente e) { throw new UnsupportedOperationException(); }
        public void deletarPorIdCliente(Integer id) { throw new UnsupportedOperationException(); }
        public List<EnderecoCliente> listar() { return List.of(); }
        public List<EnderecoCliente> listarPorIdCliente(Integer id) { return List.of(); }
    }
}
