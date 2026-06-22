package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.EstacionamientoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.EstacionamientoJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class EstacionamientoRepositoryAdapter implements EstacionamientoRepositoryPort {

    private final EstacionamientoJpaRepository repository;

    public EstacionamientoRepositoryAdapter(EstacionamientoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<EstacionamientoModel> buscarPorId(Long id) {
        return repository.findById(id).map(EstacionamientoMapper::toModel);
    }

    @Override
    public EstacionamientoModel guardar(EstacionamientoModel modelo) {
        return EstacionamientoMapper.toModel(repository.save(EstacionamientoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EstacionamientoModel> buscarPorCondominio(Long idCondominio) {
        return repository.findByIdCondominioOrderByNumeroAsc(idCondominio)
                .stream()
                .map(EstacionamientoMapper::toModel)
                .toList();
    }

    @Override
    public long contarPorCondominio(Long idCondominio) {
        return repository.countByIdCondominio(idCondominio);
    }
}
