package com.example.P1Eventos.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    // Aqui você injetará o seu serviço de token quando ele estiver pronto
    // private final TokenService tokenService; 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = recuperarToken(request);

        // EXEMPLO SIMULADO PARA COMPILAÇÃO: 
        // Na prática, você validará o token real usando a biblioteca jjwt
        if (token != null && token.equals("token-valido-admin-teste")) {
            // Se o token for do admin, injeta a ROLE_ADMIN no contexto
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            var authentication = new UsernamePasswordAuthenticationToken("ADM001", null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
