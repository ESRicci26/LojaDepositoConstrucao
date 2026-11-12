package br.com.javaricci.apresentacao.web.formulario;

import jakarta.validation.constraints.NotBlank;

/**
 * Dados do formulário de login.
 */
public class LoginFormulario {

    @NotBlank(message = "Informe o usuário.")
    private String login;

    @NotBlank(message = "Informe a senha.")
    private String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
