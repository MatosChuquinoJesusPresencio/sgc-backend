package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.TorreRepositoryPort;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.shared.exception.TorreException;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.TorreJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class TorreRepositoryAdapter implements TorreRepositoryPort {

    private final TorreJpaRepository repository;

    public TorreRepositoryAdapter(TorreJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<TorreModel> buscarPorId(Long id) {
        return repository.findById(id).map(TorreMapper::toModel);
    }

    @Override
    public TorreModel guardar(TorreModel modelo) {
        if (modelo.getId() != null) {
            var entity = repository.findById(modelo.getId())
                .orElseThrow(TorreException::noEncontrado);
            TorreMapper.applyToEntity(modelo, entity);
            return TorreMapper.toModel(repository.save(entity));
        }
        return TorreMapper.toModel(repository.save(TorreMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
