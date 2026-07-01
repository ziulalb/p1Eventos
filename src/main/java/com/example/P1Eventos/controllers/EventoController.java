package com.example.P1Eventos.controllers;

import com.example.P1Eventos.entities.Evento;
import com.example.P1Eventos.entities.Subevento;
import com.example.P1Eventos.repositories.EventoRepository;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@Validated
public class EventoController {

    private final EventoRepository eventoRepository;

    // 1. Endpoint de CRUD (Listagem)
    @GetMapping
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    // 2. Operação Multitabela com Validação no Parâmetro da Rota (@Positive)
    @GetMapping("/{id}/subeventos")
    public ResponseEntity<List<Subevento>> listarSubeventosDoEvento(
            @PathVariable @Positive(message = "O ID do evento deve ser um número positivo.") Long id) {
        
        return eventoRepository.findById(id)
                .map(evento -> ResponseEntity.ok(evento.getSubeventos()))
                .orElse(ResponseEntity.notFound().build());
    }
}
