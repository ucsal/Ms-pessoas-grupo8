package com.example.mspessoas.controller;

import com.example.mspessoas.dto.InstituicaoRequest;
import com.example.mspessoas.entity.InstituicaoEnsinoSuperior;
import com.example.mspessoas.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Gerenciamento de Instituições de Ensino Superior (IES) pelo administrador.
 *
 * Endpoints:
 *   GET  /admin/ies        → lista todas as IES
 *   GET  /admin/ies/{id}   → busca IES por ID
 *   POST /admin/ies        → cadastra nova IES
 *   PUT  /admin/ies/{id}   → atualiza IES
 */
@RestController
@RequestMapping("/admin/ies")
public class AdminInstituicaoController {

    private final InstituicaoService service;

    public AdminInstituicaoController(InstituicaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoEnsinoSuperior>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoEnsinoSuperior> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<InstituicaoEnsinoSuperior> salvar(
            @Valid @RequestBody InstituicaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoEnsinoSuperior> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody InstituicaoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }
}
