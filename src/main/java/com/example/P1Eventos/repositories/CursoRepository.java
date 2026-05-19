package com.example.P1Eventos.repositories;

import com.example.P1Eventos.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
