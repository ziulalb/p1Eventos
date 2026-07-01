package com.example.P1Eventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
    @NotBlank(message = "A matrícula é obrigatória.")
    @Size(min = 4, max = 20, message = "A matrícula deve ter entre 4 e 20 caracteres.")
    String matricula,

    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String emailInstitucional,

    String telefone,
    Long cursoId
) {}
