package com.example.mspessoas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, length = 150)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;

    // username do ms-auth, usado para vincular o professor ao token JWT
    @Column(nullable = false, unique = true, length = 120)
    private String username;

    @ManyToOne(optional = false)
    @JoinColumn(name = "escola_id", nullable = false)
    @JsonIgnoreProperties("professores")
    private Escola escola;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormacaoProfessor> formacoes = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Escola getEscola() { return escola; }
    public void setEscola(Escola escola) { this.escola = escola; }

    public List<FormacaoProfessor> getFormacoes() { return formacoes; }
    public void setFormacoes(List<FormacaoProfessor> formacoes) { this.formacoes = formacoes; }
}
