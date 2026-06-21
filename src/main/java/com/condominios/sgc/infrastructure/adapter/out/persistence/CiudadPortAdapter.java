package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.catalog.CiudadRepositoryPort;
import com.condominios.sgc.domain.model.catalog.CiudadModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.CiudadMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.catalog.CiudadJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CiudadPortAdapter implements CiudadRepositoryPort {

    private final CiudadJpaRepository repository;

    public CiudadPortAdapter(CiudadJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CiudadModel> buscarPorId(Long id) {
        return repository.findById(id).map(CiudadMapper::toModel);
    }

    @Override
    public CiudadModel guardar(CiudadModel modelo) {
        return CiudadMapper.toModel(repository.save(CiudadMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
