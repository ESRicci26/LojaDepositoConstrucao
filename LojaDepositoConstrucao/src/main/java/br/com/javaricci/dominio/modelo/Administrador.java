package br.com.javaricci.dominio.modelo;

/**
 * Usuário com permissão de acesso ao sistema.
 */
public class Administrador {

    private String login;
    private String senhaCriptografada;

    public Administrador() {
    }

    public Administrador(String login, String senhaCriptografada) {
        this.login = login;
        this.senhaCriptografada = senhaCriptografada;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    public void setSenhaCriptografada(String senhaCriptografada) {
        this.senhaCriptografada = senhaCriptografada;
    }
}
