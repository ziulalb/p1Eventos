package com.example.P1Eventos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome da sala é obrigatório.")
    @Column(nullable = false, length = 50)
    private String nomeSala;

    @NotBlank(message = "O bloco é obrigatório.")
    @Column(nullable = false, length = 10)
    private String bloco;

    @NotNull(message = "A capacidade é obrigatória.")
    @Positive(message = "A capacidade deve ser maior que zero.")
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
