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
@Table(name = "locais")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nomeSala;

    @Column(nullable = false, length = 10)
    private String bloco;

    @Column(nullable = false)
    private Integer capacidadeReal;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "local")
    private List<Subevento> subeventos = new ArrayList<>();
}
