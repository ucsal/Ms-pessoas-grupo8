package com.example.mspessoas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfessorRequest {

    @NotBlank
    private String matricula;

    @NotBlank
    private String nomeCompleto;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @NotNull
    private Long escolaId;

    private Boolean ativo = true;

    /**
     * Username que o professor usará para fazer login no ms-auth.
     * Deve ser informado na criação para vincular o professor ao seu usuario.
     * No monolito original esse campo criava o Usuario no mesmo banco;
     * aqui ele é apenas armazenado na entidade Professor para que
     * o JwtAuthenticationFilter consiga resolver "quem é esse token".
     */
    @NotBlank
    private String username;

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Long getEscolaId() { return escolaId; }
    public void setEscolaId(Long escolaId) { this.escolaId = escolaId; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
