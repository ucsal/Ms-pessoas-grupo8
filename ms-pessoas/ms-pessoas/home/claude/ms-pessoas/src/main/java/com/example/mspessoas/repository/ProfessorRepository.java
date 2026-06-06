package com.example.mspessoas.repository;

import com.example.mspessoas.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUsername(String username);
    boolean existsByMatricula(String matricula);
    boolean existsByEmail(String email);
}
