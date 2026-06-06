package com.example.mspessoas.client;

import com.example.mspessoas.dto.BibliografiaRequest;
import com.example.mspessoas.dto.ProgramaResumoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign Client que chama o ms-academico.
 *
 * O name="ms-academico" resolve via Eureka (service discovery).
 * Se o grupo nao usar Eureka, troque por url="${ms-academico.url}"
 * e defina ms-academico.url=http://localhost:8083 no application.properties.
 *
 * O ms-academico precisa expor esses endpoints internos (prefixo /interno)
 * para uso entre microservicos. Eles nao precisam ser expostos no API Gateway.
 */
@FeignClient(name = "ms-academico")
public interface AcademicoClient {

    /**
     * Retorna todos os programas de disciplina vinculados ao professor.
     * O ms-academico filtra pelo professorId.
     */
    @GetMapping("/interno/programas")
    List<ProgramaResumoResponse> listarProgramasPorProfessor(
            @RequestParam("professorId") Long professorId
    );

    /**
     * Retorna somente os programas incompletos do professor.
     */
    @GetMapping("/interno/programas/incompletos")
    List<ProgramaResumoResponse> listarProgramasIncompletosPorProfessor(
            @RequestParam("professorId") Long professorId
    );

    /**
     * Verifica se o programa pertence ao professor antes de permitir edicao.
     * Retorna 200 se valido, 403 se nao pertencer ao professor.
     */
    @GetMapping("/interno/programas/{programaId}/validar-professor")
    void validarProgramaDoProfessor(
            @PathVariable("programaId") Long programaId,
            @RequestParam("professorId") Long professorId
    );

    /**
     * Adiciona uma bibliografia basica a um programa via ms-academico.
     */
    @PostMapping("/interno/programas/{programaId}/bibliografias/basicas")
    Object adicionarBibliografiaBasica(
            @PathVariable("programaId") Long programaId,
            @RequestBody BibliografiaRequest request
    );

    /**
     * Adiciona uma bibliografia complementar a um programa via ms-academico.
     */
    @PostMapping("/interno/programas/{programaId}/bibliografias/complementares")
    Object adicionarBibliografiaComplementar(
            @PathVariable("programaId") Long programaId,
            @RequestBody BibliografiaRequest request
    );
}
