package br.com.javaricci.apresentacao.web.seguranca;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Bloqueia o acesso às páginas protegidas quando não há administrador autenticado.
 */
public class InterceptadorAutenticacao implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest requisicao, HttpServletResponse resposta, Object manipulador)
            throws Exception {
        HttpSession sessao = requisicao.getSession(false);
        boolean autenticado = sessao != null
                && sessao.getAttribute(SessaoAdministrador.ATRIBUTO_LOGIN) != null;

        if (!autenticado) {
            resposta.sendRedirect(requisicao.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
