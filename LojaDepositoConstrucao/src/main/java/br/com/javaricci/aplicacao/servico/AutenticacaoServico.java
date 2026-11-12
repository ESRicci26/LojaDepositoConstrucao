package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.porta.entrada.AutenticacaoCasoUso;
import br.com.javaricci.aplicacao.porta.saida.AdministradorRepositorio;
import br.com.javaricci.aplicacao.porta.saida.CodificadorSenha;
import org.springframework.stereotype.Service;

/**
 * Valida as credenciais informadas contra os administradores cadastrados.
 */
@Service
public class AutenticacaoServico implements AutenticacaoCasoUso {

    private final AdministradorRepositorio administradorRepositorio;
    private final CodificadorSenha codificadorSenha;

    public AutenticacaoServico(AdministradorRepositorio administradorRepositorio,
                               CodificadorSenha codificadorSenha) {
        this.administradorRepositorio = administradorRepositorio;
        this.codificadorSenha = codificadorSenha;
    }

    @Override
    public boolean autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return false;
        }
        return administradorRepositorio.buscarPorLogin(login)
                .map(administrador -> codificadorSenha.confere(senha, administrador.getSenhaCriptografada()))
                .orElse(false);
    }
}
