package br.com.javaricci.apresentacao.web.controlador;

import br.com.javaricci.apresentacao.web.formulario.LoginFormulario;
import br.com.javaricci.apresentacao.web.seguranca.SessaoAdministrador;
import br.com.javaricci.aplicacao.porta.entrada.AutenticacaoCasoUso;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Login e logout do administrador.
 */
@Controller
public class LoginControlador {

    private final AutenticacaoCasoUso autenticacaoCasoUso;

    public LoginControlador(AutenticacaoCasoUso autenticacaoCasoUso) {
        this.autenticacaoCasoUso = autenticacaoCasoUso;
    }

    @GetMapping("/login")
    public String exibirLogin(HttpServletRequest requisicao, Model modelo,
                              @RequestParam(name = "saiu", required = false) String saiu) {
        if (usuarioAutenticado(requisicao)) {
            return "redirect:/";
        }
        modelo.addAttribute("formulario", new LoginFormulario());
        modelo.addAttribute("saiu", saiu != null);
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@Valid @ModelAttribute("formulario") LoginFormulario formulario,
                             BindingResult resultado, HttpServletRequest requisicao, Model modelo) {
        if (resultado.hasErrors()) {
            return "login";
        }

        if (!autenticacaoCasoUso.autenticar(formulario.getLogin(), formulario.getSenha())) {
            modelo.addAttribute("erroLogin", "Credenciais incorretas!");
            formulario.setSenha(null);
            return "login";
        }

        HttpSession sessao = requisicao.getSession();
        requisicao.changeSessionId(); // evita fixação de sessão
        sessao.setAttribute(SessaoAdministrador.ATRIBUTO_LOGIN, formulario.getLogin());
        return "redirect:/";
    }

    @PostMapping("/sair")
    public String sair(HttpServletRequest requisicao) {
        HttpSession sessao = requisicao.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }
        return "redirect:/login?saiu";
    }

    private boolean usuarioAutenticado(HttpServletRequest requisicao) {
        HttpSession sessao = requisicao.getSession(false);
        return sessao != null && sessao.getAttribute(SessaoAdministrador.ATRIBUTO_LOGIN) != null;
    }
}
