package com.example.mspessoas.controller;

import com.example.mspessoas.dto.BibliografiaRequest;
import com.example.mspessoas.dto.FormacaoProfessorRequest;
import com.example.mspessoas.dto.ProfessorPortalResponse;
import com.example.mspessoas.dto.ProgramaResumoResponse;
import com.example.mspessoas.entity.FormacaoProfessor;
import com.example.mspessoas.service.ProfessorService;
import com.example.mspessoas.service.ProgramaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Portal do professor: endpoints exclusivos para o professor autenticado.
 *
 * Todos os endpoints extraem o username do token JWT via Authentication.getName().
 * Nao ha passagem de ID na URL — o professor so acessa seus proprios dados.
 *
 * Endpoints delegados ao ms-academico (via ProgramaService + AcademicoClient):
 *   - GET  /professor/programas
 *   - GET  /professor/programas/incompletos
 *   - POST /professor/programas/{id}/bibliografias/basicas
 *   - POST /professor/programas/{id}/bibliografias/complementares
 *
 * Endpoints resolvidos localmente no ms-pessoas:
 *   - GET  /professor/me
 *   - POST /professor/me/formacoes
 */
@RestController
@RequestMapping("/professor")
public class ProfessorPortalController {

    private final ProfessorService professorService;
    private final ProgramaService programaService;

    public ProfessorPortalController(ProfessorService professorService,
                                     ProgramaService programaService) {
        this.professorService = professorService;
        this.programaService = programaService;
    }

    /**
     * Retorna os dados cadastrais do professor autenticado,
     * incluindo escola e lista de formacoes academicas.
     *
     * GET /professor/me
     */
    @GetMapping("/me")
    public ResponseEntity<ProfessorPortalResponse> meuCadastro(Authentication authentication) {
        return ResponseEntity.ok(
                professorService.buscarPortalPorUsername(authentication.getName())
        );
    }

    /**
     * Adiciona uma nova formacao academica ao professor autenticado.
     *
     * POST /professor/me/formacoes
     * Body: { "categoriaTitulacao": "MESTRADO", "instituicaoConclusao": "USP",
     *         "nomeCurso": "Ciencia da Computacao", "anoConclusao": 2020 }
     */
    @PostMapping("/me/formacoes")
    public ResponseEntity<FormacaoProfessor> adicionarFormacao(
            Authentication authentication,
            @Valid @RequestBody FormacaoProfessorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(professorService.adicionarFormacaoDoProfessor(
                        authentication.getName(), request));
    }

    /**
     * Lista todos os programas de disciplina vinculados ao professor.
     * A consulta e delegada ao ms-academico via Feign.
     *
     * GET /professor/programas
     */
    @GetMapping("/programas")
    public ResponseEntity<List<ProgramaResumoResponse>> meusProgramas(
            Authentication authentication) {

        return ResponseEntity.ok(
                programaService.listarMeusProgramas(authentication.getName())
        );
    }

    /**
     * Lista somente os programas incompletos do professor autenticado.
     * Um programa e incompleto quando faltam campos obrigatorios
     * ou o numero minimo de bibliografias nao foi atingido.
     *
     * GET /professor/programas/incompletos
     */
    @GetMapping("/programas/incompletos")
    public ResponseEntity<List<ProgramaResumoResponse>> programasIncompletos(
            Authentication authentication) {

        return ResponseEntity.ok(
                programaService.listarMeusProgramasIncompletos(authentication.getName())
        );
    }

    /**
     * Adiciona uma bibliografia basica ao programa informado.
     * Valida que o programa pertence ao professor antes de prosseguir.
     *
     * POST /professor/programas/{programaId}/bibliografias/basicas
     */
    @PostMapping("/programas/{programaId}/bibliografias/basicas")
    public ResponseEntity<Object> adicionarBibliografiaBasica(
            Authentication authentication,
            @PathVariable Long programaId,
            @Valid @RequestBody BibliografiaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(programaService.adicionarBibliografiaBasica(
                        programaId, authentication.getName(), request));
    }

    /**
     * Adiciona uma bibliografia complementar ao programa informado.
     * Valida que o programa pertence ao professor antes de prosseguir.
     *
     * POST /professor/programas/{programaId}/bibliografias/complementares
     */
    @PostMapping("/programas/{programaId}/bibliografias/complementares")
    public ResponseEntity<Object> adicionarBibliografiaComplementar(
            Authentication authentication,
            @PathVariable Long programaId,
            @Valid @RequestBody BibliografiaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(programaService.adicionarBibliografiaComplementar(
                        programaId, authentication.getName(), request));
    }
}
