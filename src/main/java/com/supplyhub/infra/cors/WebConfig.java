package com.supplyhub.infra.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Se você tiver outras configurações de WebMvcConfigurer, mantenha-as aqui.
    // Exemplo:
    /*
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Adicionar formatadores personalizados
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
    */

    // REMOVIDO: Configuração de CORS, agora no SecurityConfig
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
    */
}