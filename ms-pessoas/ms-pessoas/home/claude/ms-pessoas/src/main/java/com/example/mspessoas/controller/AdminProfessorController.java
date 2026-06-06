package com.example.mspessoas.controller;

import com.example.mspessoas.dto.FormacaoProfessorRequest;
import com.example.mspessoas.dto.ProfessorRequest;
import com.example.mspessoas.entity.FormacaoProfessor;
import com.example.mspessoas.entity.Professor;
import com.example.mspessoas.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Gerenciamento de professores pelo administrador.
 *
 * Endpoints:
 *   GET    /admin/professores          → lista todos
 *   GET    /admin/professores/{id}     → busca por ID
 *   POST   /admin/professores          → cadastra novo professor
 *   PUT    /admin/professores/{id}     → atualiza professor
 *   PATCH  /admin/professores/{id}/inativar → inativa professor
 *   PATCH  /admin/professores/{id}/ativar   → reativa professor
 *   POST   /admin/professores/{id}/formacoes → adiciona formação acadêmica
 *
 * Atenção: ao criar um professor (POST), o campo "username" no body
 * deve ser o mesmo que será cadastrado no ms-auth. Os dois serviços
 * precisam ter o mesmo valor para que o token JWT funcione corretamente.
 */
@RestController
@RequestMapping("/admin/professores")
public class AdminProfessorController {

    private final ProfessorService service;

    public AdminProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Professor>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Professor> salvar(@Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Professor> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Professor> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.ativar(id));
    }

    @PostMapping("/{id}/formacoes")
    public ResponseEntity<FormacaoProfessor> adicionarFormacao(
            @PathVariable Long id,
            @Valid @RequestBody FormacaoProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.adicionarFormacaoAoProfessor(id, request));
    }
}
