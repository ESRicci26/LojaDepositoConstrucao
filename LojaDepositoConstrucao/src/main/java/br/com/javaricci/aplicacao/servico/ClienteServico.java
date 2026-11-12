package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.modelo.ClienteCompleto;
import br.com.javaricci.aplicacao.porta.entrada.ClienteCasoUso;
import br.com.javaricci.aplicacao.porta.saida.ClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.EnderecoClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.TelefoneClienteRepositorio;
import br.com.javaricci.aplicacao.porta.saida.VendaRepositorio;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Cliente;
import br.com.javaricci.dominio.modelo.EnderecoCliente;
import br.com.javaricci.dominio.modelo.TelefoneCliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Regras de negócio do cadastro de clientes, incluindo telefones e endereços.
 */
@Service
public class ClienteServico implements ClienteCasoUso {

    private final ClienteRepositorio clienteRepositorio;
    private final EnderecoClienteRepositorio enderecoRepositorio;
    private final TelefoneClienteRepositorio telefoneRepositorio;
    private final VendaRepositorio vendaRepositorio;

    public ClienteServico(ClienteRepositorio clienteRepositorio,
                          EnderecoClienteRepositorio enderecoRepositorio,
                          TelefoneClienteRepositorio telefoneRepositorio,
                          VendaRepositorio vendaRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.enderecoRepositorio = enderecoRepositorio;
        this.telefoneRepositorio = telefoneRepositorio;
        this.vendaRepositorio = vendaRepositorio;
    }

    @Override
    @Transactional
    public Integer inserir(ClienteCompleto clienteCompleto) {
        Integer idCliente = clienteRepositorio.inserir(clienteCompleto.cliente());
        clienteCompleto.cliente().setId(idCliente);
        gravarContatos(idCliente, clienteCompleto);
        return idCliente;
    }

    @Override
    @Transactional
    public void atualizar(ClienteCompleto clienteCompleto) {
        Integer idCliente = clienteCompleto.cliente().getId();
        garantirQueClienteExiste(idCliente);

        clienteRepositorio.atualizar(clienteCompleto.cliente());

        // telefones e endereços são substituídos pelo conjunto informado
        telefoneRepositorio.deletarPorIdCliente(idCliente);
        enderecoRepositorio.deletarPorIdCliente(idCliente);
        gravarContatos(idCliente, clienteCompleto);
    }

    @Override
    @Transactional
    public void deletar(Integer idCliente) {
        garantirQueClienteExiste(idCliente);

        if (vendaRepositorio.existePorIdCliente(idCliente)) {
            throw new RegraNegocioException(
                    "O cliente não pode ser excluído porque possui vendas registradas.");
        }

        telefoneRepositorio.deletarPorIdCliente(idCliente);
        enderecoRepositorio.deletarPorIdCliente(idCliente);
        clienteRepositorio.deletar(idCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteCompleto> listar() {
        Map<Integer, List<TelefoneCliente>> telefonesPorCliente = telefoneRepositorio.listar().stream()
                .collect(Collectors.groupingBy(TelefoneCliente::getIdCliente));
        Map<Integer, List<EnderecoCliente>> enderecosPorCliente = enderecoRepositorio.listar().stream()
                .collect(Collectors.groupingBy(EnderecoCliente::getIdCliente));

        return clienteRepositorio.listar().stream()
                .map(cliente -> new ClienteCompleto(
                        cliente,
                        telefonesPorCliente.getOrDefault(cliente.getId(), List.of()),
                        enderecosPorCliente.getOrDefault(cliente.getId(), List.of())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteCompleto buscarPorId(Integer idCliente) {
        Cliente cliente = clienteRepositorio.buscarPorId(idCliente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
        return new ClienteCompleto(
                cliente,
                telefoneRepositorio.listarPorIdCliente(idCliente),
                enderecoRepositorio.listarPorIdCliente(idCliente));
    }

    private void gravarContatos(Integer idCliente, ClienteCompleto clienteCompleto) {
        for (TelefoneCliente telefone : clienteCompleto.telefones()) {
            telefone.setIdCliente(idCliente);
            telefoneRepositorio.inserir(telefone);
        }
        for (EnderecoCliente endereco : clienteCompleto.enderecos()) {
            endereco.setIdCliente(idCliente);
            enderecoRepositorio.inserir(endereco);
        }
    }

    private void garantirQueClienteExiste(Integer idCliente) {
        if (clienteRepositorio.buscarPorId(idCliente).isEmpty()) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }
    }
}
