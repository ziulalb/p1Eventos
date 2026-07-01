package com.example.P1Eventos.controllers;

import com.example.P1Eventos.config.security.TokenService;
import com.example.P1Eventos.dtos.LoginRequestDTO;
import com.example.P1Eventos.dtos.LoginResponseDTO;
import com.example.P1Eventos.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoint de login e geração de token JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(
        summary = "Realizar login",
        description = "Autentica o usuário com matrícula e senha, retornando um token JWT válido por 24 horas"
    )
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.matricula(), dto.senha())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var usuario = usuarioRepository.findByMatricula(dto.matricula()).orElseThrow();
        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
