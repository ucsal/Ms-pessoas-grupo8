package com.example.mspessoas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_instituicao_ensino_superior")
public class InstituicaoEnsinoSuperior {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String nome;

    @Column(length = 300)
    private String endereco;

    @Column(length = 30)
    private String telefone;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
