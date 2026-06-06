package com.example.mspessoas.service;

import com.example.mspessoas.client.AcademicoClient;
import com.example.mspessoas.dto.BibliografiaRequest;
import com.example.mspessoas.dto.ProgramaResumoResponse;
import com.example.mspessoas.entity.Professor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestra as operacoes de programa de disciplina que envolvem
 * chamadas ao ms-academico. O ms-pessoas nao armazena programas
 * localmente — apenas delega ao servico responsavel.
 */
@Service
public class ProgramaService {

    private final AcademicoClient academicoClient;
    private final ProfessorService professorService;

    public ProgramaService(AcademicoClient academicoClient,
                           ProfessorService professorService) {
        this.academicoClient = academicoClient;
        this.professorService = professorService;
    }

    /**
     * Lista todos os programas do professor autenticado.
     */
    public List<ProgramaResumoResponse> listarMeusProgramas(String username) {
        Professor professor = professorService.buscarPorUsername(username);
        return academicoClient.listarProgramasPorProfessor(professor.getId());
    }

    /**
     * Lista somente os programas incompletos do professor autenticado.
     */
    public List<ProgramaResumoResponse> listarMeusProgramasIncompletos(String username) {
        Professor professor = professorService.buscarPorUsername(username);
        return academicoClient.listarProgramasIncompletosPorProfessor(professor.getId());
    }

    /**
     * Valida que o programa pertence ao professor antes de qualquer edicao.
     * Lanca excecao via Feign se o ms-academico retornar 403.
     */
    public void validarOwnership(Long programaId, String username) {
        Professor professor = professorService.buscarPorUsername(username);
        academicoClient.validarProgramaDoProfessor(programaId, professor.getId());
    }

    /**
     * Adiciona uma bibliografia basica ao programa informado.
     * Valida ownership antes de delegar ao ms-academico.
     */
    public Object adicionarBibliografiaBasica(Long programaId,
                                               String username,
                                               BibliografiaRequest request) {
        validarOwnership(programaId, username);
        return academicoClient.adicionarBibliografiaBasica(programaId, request);
    }

    /**
     * Adiciona uma bibliografia complementar ao programa informado.
     * Valida ownership antes de delegar ao ms-academico.
     */
    public Object adicionarBibliografiaComplementar(Long programaId,
                                                     String username,
                                                     BibliografiaRequest request) {
        validarOwnership(programaId, username);
        return academicoClient.adicionarBibliografiaComplementar(programaId, request);
    }
}
