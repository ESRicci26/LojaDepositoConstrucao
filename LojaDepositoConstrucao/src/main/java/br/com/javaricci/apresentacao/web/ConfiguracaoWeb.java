package br.com.javaricci.apresentacao.web;

import br.com.javaricci.apresentacao.web.seguranca.InterceptadorAutenticacao;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurações do Spring MVC: registra o interceptador de autenticação.
 */
@Configuration
public class ConfiguracaoWeb implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(new InterceptadorAutenticacao())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/css/**", "/js/**", "/img/**", "/favicon.ico", "/error");
    }
}
