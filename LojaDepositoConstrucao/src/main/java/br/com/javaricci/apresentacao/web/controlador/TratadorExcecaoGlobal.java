package br.com.javaricci.apresentacao.web.controlador;

import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Converte exceções de negócio em páginas de erro amigáveis.
 */
@ControllerAdvice
public class TratadorExcecaoGlobal {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarNaoEncontrado(RecursoNaoEncontradoException erro, Model modelo) {
        modelo.addAttribute("status", HttpStatus.NOT_FOUND.value());
        modelo.addAttribute("mensagem", erro.getMessage());
        return "error";
    }

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String tratarRegraNegocio(RegraNegocioException erro, Model modelo) {
        modelo.addAttribute("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        modelo.addAttribute("mensagem", erro.getMessage());
        return "error";
    }
}
