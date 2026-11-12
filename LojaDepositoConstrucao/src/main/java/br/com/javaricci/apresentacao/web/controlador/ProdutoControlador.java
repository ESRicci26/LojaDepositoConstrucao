package br.com.javaricci.apresentacao.web.controlador;

import br.com.javaricci.aplicacao.porta.entrada.ProdutoCasoUso;
import br.com.javaricci.apresentacao.web.formulario.ProdutoFormulario;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
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
 * Listagem, cadastro, edição e exclusão de produtos.
 */
@Controller
@RequestMapping("/produtos")
public class ProdutoControlador {

    private static final String VISAO_LISTA = "produto/lista";
    private static final String VISAO_FORMULARIO = "produto/formulario";

    private final ProdutoCasoUso produtoCasoUso;

    public ProdutoControlador(ProdutoCasoUso produtoCasoUso) {
        this.produtoCasoUso = produtoCasoUso;
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("produtos", produtoCasoUso.listar());
        return VISAO_LISTA;
    }

    @GetMapping("/novo")
    public String exibirFormularioNovo(Model modelo) {
        modelo.addAttribute("formulario", new ProdutoFormulario());
        prepararFormulario(modelo, "Novo produto", "/produtos");
        return VISAO_FORMULARIO;
    }

    @PostMapping
    public String inserir(@Valid @ModelAttribute("formulario") ProdutoFormulario formulario,
                          BindingResult resultado, Model modelo, RedirectAttributes redirecionamento) {
        if (!resultado.hasErrors()) {
            try {
                produtoCasoUso.inserir(formulario.paraEntidade());
                redirecionamento.addFlashAttribute("mensagemSucesso", "Produto cadastrado com sucesso!");
                return "redirect:/produtos";
            } catch (RegraNegocioException erro) {
                resultado.reject("erroNegocio", erro.getMessage());
            }
        }
        prepararFormulario(modelo, "Novo produto", "/produtos");
        return VISAO_FORMULARIO;
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("formulario", ProdutoFormulario.de(produtoCasoUso.buscarPorId(id)));
        prepararFormulario(modelo, "Atualizar produto", "/produtos/" + id);
        return VISAO_FORMULARIO;
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("formulario") ProdutoFormulario formulario,
                            BindingResult resultado, Model modelo, RedirectAttributes redirecionamento) {
        formulario.setId(id);
        if (!resultado.hasErrors()) {
            try {
                produtoCasoUso.atualizar(formulario.paraEntidade());
                redirecionamento.addFlashAttribute("mensagemSucesso", "Produto atualizado com sucesso!");
                return "redirect:/produtos";
            } catch (RegraNegocioException erro) {
                resultado.reject("erroNegocio", erro.getMessage());
            }
        }
        prepararFormulario(modelo, "Atualizar produto", "/produtos/" + id);
        return VISAO_FORMULARIO;
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Integer id, RedirectAttributes redirecionamento) {
        try {
            produtoCasoUso.deletar(id);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Produto deletado com sucesso!");
        } catch (RegraNegocioException erro) {
            redirecionamento.addFlashAttribute("mensagemErro", erro.getMessage());
        }
        return "redirect:/produtos";
    }

    private void prepararFormulario(Model modelo, String titulo, String acao) {
        modelo.addAttribute("titulo", titulo);
        modelo.addAttribute("acao", acao);
    }
}
