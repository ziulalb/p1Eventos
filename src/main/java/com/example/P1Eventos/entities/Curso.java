package com.example.P1Eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeCurso;

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
