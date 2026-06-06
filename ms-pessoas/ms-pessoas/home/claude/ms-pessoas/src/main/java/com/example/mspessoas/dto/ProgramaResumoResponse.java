package com.example.mspessoas.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Resposta recebida do ms-academico ao buscar programas do professor.
 * Os campos espelham o ProgramaDisciplinaResponse do ms-academico.
 */
public record ProgramaResumoResponse(
        Long id,
        Integer semestre,
        String ementa,
        String competenciasHabilidades,
        String conteudoProgramatico,
        String metodologia,
        String processoAvaliacao,
        LocalDate dataCadastro,
        Boolean ativo,
        Boolean incompleto,
        DisciplinaDetalhe disciplina,
        List<BibliografiaDetalhe> bibliografiasBasicas,
        List<BibliografiaDetalhe> bibliografiasComplementares
) {
    public record DisciplinaDetalhe(
            Long id,
            String sigla,
            String descricao,
            Integer cargaHoraria
    ) {}

    public record BibliografiaDetalhe(
            Long id,
            String titulo,
            String autores,
            String editora,
            String isbn,
            Integer anoPublicacao
    ) {}
}
