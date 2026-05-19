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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 150)
    private String emailInstitucional;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "organizador")
    private List<Evento> eventosOrganizados = new ArrayList<>();

    // Relacionamento N:N — Usuario inscreve-se em Eventos
    @Builder.Default
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "inscricoes_evento",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_evento")
    )
    private List<Evento> eventosInscritos = new ArrayList<>();

    // Relacionamento N:N — Usuario inscreve-se em Subeventos
    @Builder.Default
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "inscricoes_subevento",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_subevento")
    )
    private List<Subevento> subeventosInscritos = new ArrayList<>();
}
