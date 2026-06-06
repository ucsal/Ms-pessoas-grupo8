package com.example.mspessoas.service;

import com.example.mspessoas.dto.InstituicaoRequest;
import com.example.mspessoas.entity.InstituicaoEnsinoSuperior;
import com.example.mspessoas.exception.ResourceNotFoundException;
import com.example.mspessoas.repository.InstituicaoEnsinoSuperiorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {

    private final InstituicaoEnsinoSuperiorRepository repository;

    public InstituicaoService(InstituicaoEnsinoSuperiorRepository repository) {
        this.repository = repository;
    }

    public List<InstituicaoEnsinoSuperior> listar() {
        return repository.findAll();
    }

    public InstituicaoEnsinoSuperior buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IES não encontrada com id " + id));
    }

    public InstituicaoEnsinoSuperior salvar(InstituicaoRequest request) {
        InstituicaoEnsinoSuperior ies = new InstituicaoEnsinoSuperior();
        ies.setNome(request.getNome());
        ies.setEndereco(request.getEndereco());
        ies.setTelefone(request.getTelefone());
        return repository.save(ies);
    }

    public InstituicaoEnsinoSuperior atualizar(Long id, InstituicaoRequest request) {
        InstituicaoEnsinoSuperior ies = buscar(id);
        ies.setNome(request.getNome());
        ies.setEndereco(request.getEndereco());
        ies.setTelefone(request.getTelefone());
        return repository.save(ies);
    }
}
