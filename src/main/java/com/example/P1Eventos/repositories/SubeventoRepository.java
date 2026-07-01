package com.example.P1Eventos.repositories;

import com.example.P1Eventos.entities.Subevento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubeventoRepository extends JpaRepository<Subevento, Long> {
    List<Subevento> findByEventoId(Long eventoId);
}
