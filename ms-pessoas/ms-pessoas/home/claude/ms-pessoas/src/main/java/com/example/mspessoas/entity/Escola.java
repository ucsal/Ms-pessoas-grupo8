package com.example.mspessoas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_escola")
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(length = 150)
    private String coordenador;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ies_id", nullable = false)
    @JsonIgnoreProperties("escolas")
    private InstituicaoEnsinoSuperior ies;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCoordenador() { return coordenador; }
    public void setCoordenador(String coordenador) { this.coordenador = coordenador; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public InstituicaoEnsinoSuperior getIes() { return ies; }
    public void setIes(InstituicaoEnsinoSuperior ies) { this.ies = ies; }
}
