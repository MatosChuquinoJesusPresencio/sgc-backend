package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.CondominioJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CondominioRepositoryAdapter implements CondominioRepositoryPort {

    private final CondominioJpaRepository repository;

    public CondominioRepositoryAdapter(CondominioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CondominioModel> buscarPorId(Long id) {
        return repository.findWithTreeById(id).map(CondominioMapper::toModel);
    }

    @Override
    public CondominioModel guardar(CondominioModel modelo) {
        CondominioEntity entity = CondominioMapper.toEntity(modelo);
        if (entity.getTorres() != null) {
            entity.getTorres().forEach(t -> t.setCondominio(entity));
        }
        if (entity.getConfiguracion() != null) {
            entity.getConfiguracion().setCondominio(entity);
        }
        return CondominioMapper.toModel(repository.save(entity));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
