package com.example.P1Eventos.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtSecurityFilter jwtSecurityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita CSRF pois APIs REST com JWT são stateless
                .csrf(csrf -> csrf.disable())
                // Configura a sessão como STATELESS (Exigência de arquitetura JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Regras de Autorização de Rotas (Mapeadas para o seu ecossistema)
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Libera rotas públicas de Autenticação e H2 Console
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        
                        // 2. Libera as rotas de documentação do Swagger/OpenAPI
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 3. Controle de Acesso baseado nos seus perfis de negócio (Roles)
                        // Apenas ADMIN pode gerenciar locais e cursos
                        .requestMatchers("/api/locais/**", "/api/cursos/**").hasRole("ADMIN")
                        
                        // Apenas DOCENTE e ADMIN podem criar/modificar eventos e subeventos
                        .requestMatchers(HttpMethod.POST, "/api/eventos/**").hasAnyRole("DOCENTE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/subeventos/**").hasAnyRole("DOCENTE", "ADMIN")
                        
                        // DISCENTE pode visualizar eventos e se inscrever
                        .requestMatchers(HttpMethod.GET, "/api/eventos/**").hasAnyRole("DISCENTE", "DOCENTE", "ADMIN")
                        .requestMatchers("/api/inscricoes/**").hasRole("DISCENTE")

                        // Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                // Habilita a exibição do H2-Console dentro de frames das páginas
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // Adiciona o filtro JWT antes do filtro padrão do Spring Security
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Bean de gerenciamento de autenticação padrão do Spring
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Criptografia BCrypt para as senhas (Atende o requisito de Criptografia do professor)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
