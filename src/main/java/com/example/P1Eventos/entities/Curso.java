package com.example.P1Eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome do curso é obrigatório.")
    @Column(nullable = false, length = 100)
    private String nomeCurso;

    @NotBlank(message = "O departamento é obrigatório.")
    @Column(nullable = false, length = 100)
    private String departamento;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "curso")
    private List<Usuario> usuarios = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "curso")
    private List<Local> locais = new ArrayList<>();
}
