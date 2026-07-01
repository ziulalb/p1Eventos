package com.example.P1Eventos.controllers;

import com.example.P1Eventos.entities.Curso;
import com.example.P1Eventos.repositories.CursoRepository;
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
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Validated
@Tag(name = "Cursos", description = "Endpoints para gerenciamento de cursos")
public class CursoController {

    private final CursoRepository cursoRepository;

    @GetMapping
    @Operation(summary = "Listar todos os cursos", description = "Retorna uma lista com todos os cursos cadastrados")
    public ResponseEntity<List<Curso>> listarTodos() {
        return ResponseEntity.ok(cursoRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID", description = "Retorna os detalhes de um curso específico com base no ID fornecido")
    public ResponseEntity<Curso> buscarPorId(@PathVariable @Positive Long id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo curso", description = "Cadastra um novo curso no sistema com validação de dados")
    public ResponseEntity<Curso> criar(@RequestBody @Valid Curso curso) {
        Curso novoCurso = cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um curso", description = "Atualiza os dados de um curso existente com base no ID")
    public ResponseEntity<Curso> atualizar(@PathVariable @Positive Long id, @RequestBody @Valid Curso cursoAtualizado) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNomeCurso(cursoAtualizado.getNomeCurso());
                    curso.setDepartamento(cursoAtualizado.getDepartamento());
                    Curso salvo = cursoRepository.save(curso);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um curso", description = "Remove um curso do sistema com base no ID fornecido")
    public ResponseEntity<Void> deletar(@PathVariable @Positive Long id) {
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
