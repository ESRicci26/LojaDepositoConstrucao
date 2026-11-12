package br.com.javaricci.apresentacao.web.controlador;

import br.com.javaricci.aplicacao.porta.entrada.ClienteCasoUso;
import br.com.javaricci.aplicacao.porta.entrada.ProdutoCasoUso;
import br.com.javaricci.aplicacao.porta.entrada.VendaCasoUso;
import br.com.javaricci.apresentacao.web.formulario.ItemVendaFormulario;
import br.com.javaricci.apresentacao.web.formulario.VendaFormulario;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Registro de novas vendas.
 */
@Controller
@RequestMapping("/vendas")
public class VendaControlador {

    private static final String VISAO_FORMULARIO = "venda/formulario";

    private final VendaCasoUso vendaCasoUso;
    private final ClienteCasoUso clienteCasoUso;
    private final ProdutoCasoUso produtoCasoUso;

    public VendaControlador(VendaCasoUso vendaCasoUso,
                            ClienteCasoUso clienteCasoUso,
                            ProdutoCasoUso produtoCasoUso) {
        this.vendaCasoUso = vendaCasoUso;
        this.clienteCasoUso = clienteCasoUso;
        this.produtoCasoUso = produtoCasoUso;
    }

    @GetMapping("/nova")
    public String exibirFormulario(Model modelo) {
        VendaFormulario formulario = new VendaFormulario();
        formulario.getItens().add(new ItemVendaFormulario());
        modelo.addAttribute("formulario", formulario);
        carregarOpcoes(modelo);
        return VISAO_FORMULARIO;
    }

    @PostMapping
    public String inserir(@Valid @ModelAttribute("formulario") VendaFormulario formulario,
                          BindingResult resultado, Model modelo, RedirectAttributes redirecionamento) {
        if (!resultado.hasErrors()) {
            try {
                Integer idVenda = vendaCasoUso.inserir(formulario.paraComando());
                redirecionamento.addFlashAttribute("mensagemSucesso", "Venda registrada com sucesso!");
                return "redirect:/relatorios/" + idVenda;
            } catch (RegraNegocioException | RecursoNaoEncontradoException erro) {
                resultado.reject("erroNegocio", erro.getMessage());
            }
        }
        if (formulario.getItens().isEmpty()) {
            formulario.getItens().add(new ItemVendaFormulario());
        }
        carregarOpcoes(modelo);
        return VISAO_FORMULARIO;
    }

    private void carregarOpcoes(Model modelo) {
        modelo.addAttribute("clientes", clienteCasoUso.listar());
        modelo.addAttribute("produtos", produtoCasoUso.listar());
    }
}
