package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.catalog.PaisRepositoryPort;
import com.condominios.sgc.domain.model.catalog.PaisModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.PaisMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.catalog.PaisJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PaisPortAdapter implements PaisRepositoryPort {

    private final PaisJpaRepository repository;

    public PaisPortAdapter(PaisJpaRepository repository) {
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
