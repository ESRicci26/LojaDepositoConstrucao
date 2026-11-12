package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.modelo.DetalheVenda;
import br.com.javaricci.aplicacao.modelo.ItemVendaComando;
import br.com.javaricci.aplicacao.modelo.ItemVendaDetalhado;
import br.com.javaricci.aplicacao.modelo.RegistrarVendaComando;
import br.com.javaricci.aplicacao.modelo.ResumoVenda;
import br.com.javaricci.aplicacao.porta.entrada.VendaCasoUso;
import br.com.javaricci.aplicacao.porta.saida.ClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.EnderecoClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.ItemVendaRepositorio;
import br.com.javaricci.aplicacao.porta.saida.ProdutoRepositorio;
import br.com.javaricci.aplicacao.porta.saida.VendaRepositorio;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import br.com.javaricci.dominio.modelo.ItemVenda;
import br.com.javaricci.dominio.modelo.Produto;
import br.com.javaricci.dominio.modelo.Venda;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Regras de negócio de vendas: cálculo de valores, controle de estoque e pagamento.
 */
@Service
public class VendaServico implements VendaCasoUso {

    private final VendaRepositorio vendaRepositorio;
    private final ItemVendaRepositorio itemVendaRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final EnderecoClienteRepositorio enderecoRepositorio;
    private final ProdutoRepositorio produtoRepositorio;

    public VendaServico(VendaRepositorio vendaRepositorio,
                        ItemVendaRepositorio itemVendaRepositorio,
                        ClienteRepositorio clienteRepositorio,
                        EnderecoClienteRepositorio enderecoRepositorio,
                        ProdutoRepositorio produtoRepositorio) {
        this.vendaRepositorio = vendaRepositorio;
        this.itemVendaRepositorio = itemVendaRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.enderecoRepositorio = enderecoRepositorio;
        this.produtoRepositorio = produtoRepositorio;
    }

    /**
     * Registra a venda e seus itens, baixando o estoque. Tudo ocorre em uma única
     * transação: qualquer falha desfaz a venda inteira.
     */
    @Override
    @Transactional
    public Integer inserir(RegistrarVendaComando comando) {
        Cliente cliente = clienteRepositorio.buscarPorId(comando.idCliente())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        if (comando.itens() == null || comando.itens().isEmpty()) {
            throw new RegraNegocioException("Adicione ao menos um produto à venda.");
        }
        if (cliente.exigePagamentoNaVenda() && comando.dataPagamento() == null) {
            throw new RegraNegocioException("Clientes novos devem ter uma data de pagamento.");
        }

        List<ItemVenda> itens = new ArrayList<>();
        List<String> nomesProdutos = new ArrayList<>();
        BigDecimal valorTotalVenda = BigDecimal.ZERO;

        for (ItemVendaComando itemComando : comando.itens()) {
            Produto produto = produtoRepositorio.buscarPorId(itemComando.idProduto())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));

            if (itemComando.quantidade() <= 0) {
                throw new RegraNegocioException("A quantidade do produto " + produto.getNome()
                        + " deve ser maior que zero.");
            }

            BigDecimal valorItem = produto.calcularValorTotal(itemComando.quantidade());
            itens.add(new ItemVenda(null, null, produto.getId(), itemComando.quantidade(), valorItem));
            nomesProdutos.add(produto.getNome());
            valorTotalVenda = valorTotalVenda.add(valorItem);
        }

        Venda venda = new Venda(null, cliente.getId(), comando.dataPagamento(), valorTotalVenda);
        Integer idVenda = vendaRepositorio.inserir(venda);

        for (int i = 0; i < itens.size(); i++) {
            ItemVenda item = itens.get(i);
            if (!produtoRepositorio.baixarEstoque(item.getCodigoProduto(), item.getQuantidade())) {
                throw new RegraNegocioException("Estoque insuficiente para o produto: " + nomesProdutos.get(i));
            }
            item.setIdVenda(idVenda);
            itemVendaRepositorio.inserir(item);
        }
        return idVenda;
    }

    @Override
    @Transactional
    public void atualizarPagamento(Integer idVenda, LocalDate dataPagamento) {
        if (dataPagamento == null) {
            throw new RegraNegocioException("Informe a data de pagamento.");
        }
        Venda venda = buscarVenda(idVenda);
        venda.setDataPagamento(dataPagamento);
        vendaRepositorio.atualizar(venda);
    }

    /**
     * Exclui a venda e devolve ao estoque os produtos retirados.
     */
    @Override
    @Transactional
    public void deletar(Integer idVenda) {
        buscarVenda(idVenda);

        for (ItemVenda item : itemVendaRepositorio.listarPorIdVenda(idVenda)) {
            produtoRepositorio.devolverEstoque(item.getCodigoProduto(), item.getQuantidade());
        }
        itemVendaRepositorio.deletarPorIdVenda(idVenda);
        vendaRepositorio.deletar(idVenda);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumoVenda> listar() {
        Map<Integer, String> nomesPorCliente = clienteRepositorio.listar().stream()
                .collect(Collectors.toMap(Cliente::getId, Cliente::getNome));

        return vendaRepositorio.listar().stream()
                .map(venda -> new ResumoVenda(
                        venda.getId(),
                        nomesPorCliente.getOrDefault(venda.getIdCliente(), "Cliente não encontrado"),
                        venda.getDataPagamento(),
                        venda.getValorTotal()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DetalheVenda detalhar(Integer idVenda) {
        Venda venda = buscarVenda(idVenda);
        Cliente cliente = clienteRepositorio.buscarPorId(venda.getIdCliente())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente da venda não encontrado."));

        EnderecoCliente endereco = enderecoRepositorio.listarPorIdCliente(cliente.getId()).stream()
                .findFirst()
                .orElse(null);

        Map<Integer, Produto> produtosPorCodigo = produtoRepositorio.listar().stream()
                .collect(Collectors.toMap(Produto::getId, Function.identity()));

        List<ItemVendaDetalhado> itens = itemVendaRepositorio.listarPorIdVenda(idVenda).stream()
                .map(item -> detalharItem(item, produtosPorCodigo.get(item.getCodigoProduto())))
                .toList();

        return new DetalheVenda(venda, cliente, endereco, itens);
    }

    private ItemVendaDetalhado detalharItem(ItemVenda item, Produto produto) {
        BigDecimal valorUnitario = item.getValorTotal()
                .divide(BigDecimal.valueOf(item.getQuantidade()), 2, RoundingMode.HALF_UP);
        String nomeProduto = produto != null ? produto.getNome() : "Produto não encontrado";
        return new ItemVendaDetalhado(item.getCodigoProduto(), nomeProduto,
                item.getQuantidade(), valorUnitario, item.getValorTotal());
    }

    private Venda buscarVenda(Integer idVenda) {
        return vendaRepositorio.buscarPorId(idVenda)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Venda não encontrada."));
    }
}
