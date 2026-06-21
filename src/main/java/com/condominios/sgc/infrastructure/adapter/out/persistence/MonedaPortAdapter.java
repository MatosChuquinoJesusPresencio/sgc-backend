package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.catalog.MonedaRepositoryPort;
import com.condominios.sgc.domain.model.catalog.MonedaModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.MonedaMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.catalog.MonedaJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class MonedaPortAdapter implements MonedaRepositoryPort {

    private final MonedaJpaRepository repository;

    public MonedaPortAdapter(MonedaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MonedaModel> buscarPorId(Long id) {
        return repository.findById(id).map(MonedaMapper::toModel);
    }

    @Override
    public MonedaModel guardar(MonedaModel modelo) {
        return MonedaMapper.toModel(repository.save(MonedaMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
