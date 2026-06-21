package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.domain.model.PaisModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.PaisMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.PaisJpaRepository;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PaisRepositoryAdapter implements PaisRepositoryPort {

    private final PaisJpaRepository repository;

    public PaisRepositoryAdapter(PaisJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PaisModel> buscarPorId(Long id) {
        return repository.findById(id).map(PaisMapper::toModel);
    }

    @Override
    public PaisModel guardar(PaisModel modelo) {
        return PaisMapper.toModel(repository.save(PaisMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
