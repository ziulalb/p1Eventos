package com.example.P1Eventos.controllers;

import com.example.P1Eventos.entities.Evento;
import com.example.P1Eventos.entities.Subevento;
import com.example.P1Eventos.repositories.EventoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Eventos", description = "Endpoints para consulta de eventos acadêmicos")
public class EventoController {

    private final EventoRepository eventoRepository;

    @GetMapping
    @Operation(summary = "Listar todos os eventos", description = "Retorna todos os eventos cadastrados no sistema")
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}/subeventos")
    @Operation(
        summary = "Listar subeventos de um evento",
        description = "Retorna todos os subeventos (palestras, workshops, minicursos) vinculados a um evento. Operação multitabela: Evento + Subevento + Local."
    )
    public ResponseEntity<List<Subevento>> listarSubeventosDoEvento(
            @PathVariable @Positive(message = "O ID do evento deve ser um número positivo.") Long id) {

        return eventoRepository.findById(id)
                .map(evento -> ResponseEntity.ok(evento.getSubeventos()))
                .orElse(ResponseEntity.notFound().build());
    }
}
