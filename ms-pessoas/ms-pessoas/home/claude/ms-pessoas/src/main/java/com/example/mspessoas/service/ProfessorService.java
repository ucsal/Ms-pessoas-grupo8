package com.example.mspessoas.service;

import com.example.mspessoas.dto.FormacaoProfessorRequest;
import com.example.mspessoas.dto.ProfessorPortalResponse;
import com.example.mspessoas.dto.ProfessorRequest;
import com.example.mspessoas.entity.Escola;
import com.example.mspessoas.entity.FormacaoProfessor;
import com.example.mspessoas.entity.Professor;
import com.example.mspessoas.exception.BusinessRuleException;
import com.example.mspessoas.exception.ResourceNotFoundException;
import com.example.mspessoas.repository.EscolaRepository;
import com.example.mspessoas.repository.FormacaoProfessorRepository;
import com.example.mspessoas.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final EscolaRepository escolaRepository;
    private final FormacaoProfessorRepository formacaoProfessorRepository;

    public ProfessorService(ProfessorRepository professorRepository,
                            EscolaRepository escolaRepository,
                            FormacaoProfessorRepository formacaoProfessorRepository) {
        this.professorRepository = professorRepository;
        this.escolaRepository = escolaRepository;
        this.formacaoProfessorRepository = formacaoProfessorRepository;
    }

    public List<Professor> listar() {
        return professorRepository.findAll();
    }

    public Professor buscar(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com id " + id));
    }

    /**
     * Busca o professor pelo username armazenado no token JWT.
     * O campo username da entidade Professor deve ser igual
     * ao username cadastrado no ms-auth.
     */
    public Professor buscarPorUsername(String username) {
        return professorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor não encontrado para o usuário: " + username));
    }

    /**
     * Monta o ProfessorPortalResponse — usado pelo endpoint GET /professor/me.
     */
    @Transactional(readOnly = true)
    public ProfessorPortalResponse buscarPortalPorUsername(String username) {
        Professor professor = buscarPorUsername(username);

        List<ProfessorPortalResponse.FormacaoResumo> formacoes = professor.getFormacoes() == null
                ? List.of()
                : professor.getFormacoes().stream()
                        .map(f -> new ProfessorPortalResponse.FormacaoResumo(
                                f.getId(),
                                f.getCategoriaTitulacao().name(),
                                f.getInstituicaoConclusao(),
                                f.getNomeCurso(),
                                f.getAnoConclusao()
                        ))
                        .toList();

        ProfessorPortalResponse.EscolaResumo escolaResumo = professor.getEscola() == null
                ? null
                : new ProfessorPortalResponse.EscolaResumo(
                        professor.getEscola().getId(),
                        professor.getEscola().getNome()
                );

        return new ProfessorPortalResponse(
                professor.getId(),
                professor.getMatricula(),
                professor.getNomeCompleto(),
                professor.getEmail(),
                professor.getTelefone(),
                professor.getAtivo(),
                escolaResumo,
                formacoes
        );
    }

    /**
     * Cria um novo professor.
     * O campo username é obrigatório: é o mesmo que será cadastrado no ms-auth,
     * e serve para resolver o professor a partir do token JWT.
     */
    public Professor salvar(ProfessorRequest request) {
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + request.getEscolaId()));

        if (professorRepository.existsByMatricula(request.getMatricula())) {
            throw new BusinessRuleException("Já existe professor com essa matrícula");
        }
        if (professorRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Já existe professor com esse e-mail");
        }

        Professor professor = new Professor();
        professor.setMatricula(request.getMatricula());
        professor.setNomeCompleto(request.getNomeCompleto());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());
        professor.setAtivo(request.getAtivo() == null || request.getAtivo());
        professor.setUsername(request.getUsername());
        professor.setEscola(escola);
        return professorRepository.save(professor);
    }

    public Professor atualizar(Long id, ProfessorRequest request) {
        Professor professor = buscar(id);
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + request.getEscolaId()));

        professor.setMatricula(request.getMatricula());
        professor.setNomeCompleto(request.getNomeCompleto());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());
        professor.setAtivo(request.getAtivo() == null || request.getAtivo());
        professor.setUsername(request.getUsername());
        professor.setEscola(escola);
        return professorRepository.save(professor);
    }

    public Professor inativar(Long id) {
        Professor professor = buscar(id);
        professor.setAtivo(false);
        return professorRepository.save(professor);
    }

    public Professor ativar(Long id) {
        Professor professor = buscar(id);
        professor.setAtivo(true);
        return professorRepository.save(professor);
    }

    /**
     * Adiciona uma formação acadêmica ao professor autenticado (usado pelo portal).
     */
    public FormacaoProfessor adicionarFormacaoDoProfessor(String username,
                                                           FormacaoProfessorRequest request) {
        Professor professor = buscarPorUsername(username);
        return adicionarFormacao(professor, request);
    }

    /**
     * Adiciona uma formação a um professor pelo ID (usado pelo admin).
     */
    public FormacaoProfessor adicionarFormacaoAoProfessor(Long professorId,
                                                           FormacaoProfessorRequest request) {
        Professor professor = buscar(professorId);
        return adicionarFormacao(professor, request);
    }

    private FormacaoProfessor adicionarFormacao(Professor professor,
                                                 FormacaoProfessorRequest request) {
        FormacaoProfessor formacao = new FormacaoProfessor();
        formacao.setProfessor(professor);
        formacao.setCategoriaTitulacao(request.getCategoriaTitulacao());
        formacao.setInstituicaoConclusao(request.getInstituicaoConclusao());
        formacao.setNomeCurso(request.getNomeCurso());
        formacao.setAnoConclusao(request.getAnoConclusao());
        return formacaoProfessorRepository.save(formacao);
    }
}
