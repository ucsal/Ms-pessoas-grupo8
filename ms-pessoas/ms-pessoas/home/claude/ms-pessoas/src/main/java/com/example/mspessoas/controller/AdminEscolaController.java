package com.example.mspessoas.controller;

import com.example.mspessoas.dto.EscolaRequest;
import com.example.mspessoas.entity.Escola;
import com.example.mspessoas.service.EscolaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Gerenciamento de escolas pelo administrador.
 *
 * Endpoints:
 *   GET    /admin/escolas          → lista todas
 *   GET    /admin/escolas/{id}     → busca por ID
 *   POST   /admin/escolas          → cadastra nova escola (requer iesId válido)
 *   PUT    /admin/escolas/{id}     → atualiza escola
 *   PATCH  /admin/escolas/{id}/inativar → inativa escola
 */
@RestController
@RequestMapping("/admin/escolas")
public class AdminEscolaController {

    private final EscolaService service;

    public AdminEscolaController(EscolaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Escola>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escola> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Escola> salvar(@Valid @RequestBody EscolaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Escola> atualizar(@PathVariable Long id,
                                             @Valid @RequestBody EscolaRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Escola> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
