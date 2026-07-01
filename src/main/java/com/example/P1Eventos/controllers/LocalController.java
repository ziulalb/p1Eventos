package com.example.P1Eventos.controllers;

import com.example.P1Eventos.entities.Local;
import com.example.P1Eventos.repositories.LocalRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locais")
@RequiredArgsConstructor
@Validated
@Tag(name = "Locais", description = "Endpoints para gerenciamento de locais dos eventos")
public class LocalController {

    private final LocalRepository localRepository;

    @GetMapping
    @Operation(summary = "Listar todos os locais", description = "Retorna uma lista com todos os locais cadastrados")
    public ResponseEntity<List<Local>> listarTodos() {
        return ResponseEntity.ok(localRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar local por ID", description = "Retorna os detalhes de um local específico com base no ID fornecido")
    public ResponseEntity<Local> buscarPorId(@PathVariable @Positive Long id) {
        return localRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo local", description = "Cadastra um novo local no sistema com validação de dados")
    public ResponseEntity<Local> criar(@RequestBody @Valid Local local) {
        Local novoLocal = localRepository.save(local);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLocal);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um local", description = "Atualiza os dados de um local existente com base no ID")
    public ResponseEntity<Local> atualizar(@PathVariable @Positive Long id, @RequestBody @Valid Local localAtualizado) {
        return localRepository.findById(id)
                .map(local -> {
                    local.setNomeSala(localAtualizado.getNomeSala());
                    local.setBloco(localAtualizado.getBloco());
                    local.setCapacidadeReal(localAtualizado.getCapacidadeReal());
                    Local salvo = localRepository.save(local);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um local", description = "Remove um local do sistema com base no ID fornecido")
    public ResponseEntity<Void> deletar(@PathVariable @Positive Long id) {
        if (!localRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        localRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
