package br.com.javaricci.apresentacao.web.controlador;

import br.com.javaricci.aplicacao.porta.entrada.ClienteCasoUso;
import br.com.javaricci.apresentacao.web.formulario.ClienteFormulario;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.TipoCliente;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Listagem, cadastro, edição e exclusão de clientes.
 */
@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    private static final String VISAO_LISTA = "cliente/lista";
    private static final String VISAO_FORMULARIO = "cliente/formulario";

    private final ClienteCasoUso clienteCasoUso;

    public ClienteControlador(ClienteCasoUso clienteCasoUso) {
        this.clienteCasoUso = clienteCasoUso;
    }

    @ModelAttribute("tiposCliente")
    public TipoCliente[] tiposCliente() {
        return TipoCliente.values();
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("clientes", clienteCasoUso.listar());
        return VISAO_LISTA;
    }

    @GetMapping("/novo")
    public String exibirFormularioNovo(Model modelo) {
        modelo.addAttribute("formulario", new ClienteFormulario());
        prepararFormulario(modelo, "Novo cliente", "/clientes");
        return VISAO_FORMULARIO;
    }

    @PostMapping
    public String inserir(@Valid @ModelAttribute("formulario") ClienteFormulario formulario,
                          BindingResult resultado, Model modelo, RedirectAttributes redirecionamento) {
        if (!resultado.hasErrors()) {
            try {
                clienteCasoUso.inserir(formulario.paraClienteCompleto());
                redirecionamento.addFlashAttribute("mensagemSucesso", "Cliente cadastrado com sucesso!");
                return "redirect:/clientes";
            } catch (RegraNegocioException erro) {
                resultado.reject("erroNegocio", erro.getMessage());
            }
        }
        prepararFormulario(modelo, "Novo cliente", "/clientes");
        return VISAO_FORMULARIO;
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("formulario", ClienteFormulario.de(clienteCasoUso.buscarPorId(id)));
        prepararFormulario(modelo, "Atualizar cliente", "/clientes/" + id);
        return VISAO_FORMULARIO;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("formulario") ClienteFormulario formulario,
                            BindingResult resultado, Model modelo, RedirectAttributes redirecionamento) {
        formulario.setId(id);
        if (!resultado.hasErrors()) {
            try {
                clienteCasoUso.atualizar(formulario.paraClienteCompleto());
                redirecionamento.addFlashAttribute("mensagemSucesso", "Cliente atualizado com sucesso!");
                return "redirect:/clientes";
            } catch (RegraNegocioException erro) {
                resultado.reject("erroNegocio", erro.getMessage());
            }
        }
        prepararFormulario(modelo, "Atualizar cliente", "/clientes/" + id);
        return VISAO_FORMULARIO;
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Integer id, RedirectAttributes redirecionamento) {
        try {
            clienteCasoUso.deletar(id);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Cliente deletado com sucesso!");
        } catch (RegraNegocioException erro) {
            redirecionamento.addFlashAttribute("mensagemErro", erro.getMessage());
        }
        return "redirect:/clientes";
    }

    private void prepararFormulario(Model modelo, String titulo, String acao) {
        modelo.addAttribute("titulo", titulo);
        modelo.addAttribute("acao", acao);
    }
}
