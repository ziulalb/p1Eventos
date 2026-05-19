package com.example.P1Eventos.repositories;

import com.example.P1Eventos.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
