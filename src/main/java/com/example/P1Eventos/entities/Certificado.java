package com.example.P1Eventos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "certificados")
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_subevento", nullable = false)
    private Subevento subevento;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false, unique = true, length = 64)
    private String hashAutenticidade;

    @Column(nullable = false)
    private LocalDateTime dataEmissao;
}
