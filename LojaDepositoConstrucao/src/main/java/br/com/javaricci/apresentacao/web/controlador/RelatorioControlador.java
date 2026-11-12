package br.com.javaricci.apresentacao.web.controlador;

import br.com.javaricci.aplicacao.porta.entrada.VendaCasoUso;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * Relatório de vendas: listagem, detalhe, registro de pagamento e exclusão.
 */
@Controller
@RequestMapping("/relatorios")
public class RelatorioControlador {

    private final VendaCasoUso vendaCasoUso;

    public RelatorioControlador(VendaCasoUso vendaCasoUso) {
        this.vendaCasoUso = vendaCasoUso;
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("vendas", vendaCasoUso.listar());
        return "relatorio/lista";
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("detalhe", vendaCasoUso.detalhar(id));
        return "relatorio/detalhe";
    }

    @PostMapping("/{id}/pagamento")
    public String atualizarPagamento(@PathVariable Integer id,
                                     @RequestParam(name = "dataPagamento", required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataPagamento,
                                     RedirectAttributes redirecionamento) {
        try {
            vendaCasoUso.atualizarPagamento(id, dataPagamento);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Pagamento registrado com sucesso!");
        } catch (RegraNegocioException erro) {
            redirecionamento.addFlashAttribute("mensagemErro", erro.getMessage());
        }
        return "redirect:/relatorios/" + id;
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Integer id, RedirectAttributes redirecionamento) {
        try {
            vendaCasoUso.deletar(id);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Venda deletada com sucesso!");
        } catch (RegraNegocioException erro) {
            redirecionamento.addFlashAttribute("mensagemErro", erro.getMessage());
        }
        return "redirect:/relatorios";
    }
}
