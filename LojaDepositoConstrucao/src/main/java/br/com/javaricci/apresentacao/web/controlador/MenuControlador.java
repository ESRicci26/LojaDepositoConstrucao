package br.com.javaricci.apresentacao.web.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Página inicial com o menu principal do sistema.
 */
@Controller
public class MenuControlador {

    @GetMapping({"/", "/menu"})
    public String exibirMenu() {
        return "menu";
    }
}
