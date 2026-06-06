package com.example.mspessoas.repository;

import com.example.mspessoas.entity.FormacaoProfessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormacaoProfessorRepository extends JpaRepository<FormacaoProfessor, Long> {
    List<FormacaoProfessor> findByProfessorId(Long professorId);
}
