package com.supplyhub.infra.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.ControllerAdviceBean;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs REST sem estado
                .cors(Customizer.withDefaults()) // Habilita CORS usando o CorsConfigurationSource bean
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público a endpoints de autenticação e documentação Swagger
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()
                        // Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )
                // Configura a gestão de sessão para ser STATELESS (sem estado), ideal para APIs REST
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS))
                // Configura o servidor de recursos OAuth2 para JWT
                .oauth2ResourceServer(server -> server
                        .jwt(Customizer.withDefaults()) // Usa o JWT decoder padrão
                        // Define handlers para erros de autenticação e acesso negado
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .build();
    }

    /**
     * Configura as regras de CORS para a aplicação.
     * Este bean será usado pelo Spring Security quando Customizer.withDefaults() for invocado.
     * @return CorsConfigurationSource com as configurações de CORS.
     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Expõe o AuthenticationManager como um bean.
     * Necessário para realizar a autenticação de usuários (ex: no AuthController).
     * @param authenticationConfiguration Configuração de autenticação.
     * @return AuthenticationManager.
     * @throws Exception se houver erro na configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Define o codificador de senhas a ser usado pelo Spring Security.
     * Essencial para armazenar senhas de forma segura (hash).
     * @return PasswordEncoder (BCryptPasswordEncoder).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}