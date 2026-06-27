package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.domain.model.PaisModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.PaisMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.PaisJpaRepository;

@Component
public class PaisRepositoryAdapter implements PaisRepositoryPort {

    private final PaisJpaRepository repository;

    public PaisRepositoryAdapter(PaisJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<PaisModel> buscarPorId(Long id) {
        return repository.findById(id).map(PaisMapper::toModel);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaisModel> listarTodos() {
        return repository.findAll().stream().map(PaisMapper::toModel).toList();
    }
}
