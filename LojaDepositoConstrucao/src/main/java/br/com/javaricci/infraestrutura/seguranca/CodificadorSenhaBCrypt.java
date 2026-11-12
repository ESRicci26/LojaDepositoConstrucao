package br.com.javaricci.infraestrutura.seguranca;

import br.com.javaricci.aplicacao.porta.saida.CodificadorSenha;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementação da porta de senhas usando o algoritmo BCrypt.
 */
@Component
public class CodificadorSenhaBCrypt implements CodificadorSenha {

    private final BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();

    @Override
    public String codificar(String senhaPura) {
        return codificador.encode(senhaPura);
    }

    @Override
    public boolean confere(String senhaPura, String senhaCriptografada) {
        return codificador.matches(senhaPura, senhaCriptografada);
    }
}
