package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.VehiculoJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class VehiculoRepositoryAdapter implements VehiculoRepositoryPort {

    private final VehiculoJpaRepository repository;

    public VehiculoRepositoryAdapter(VehiculoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<VehiculoModel> buscarPorId(Long id) {
        return repository.findById(id).map(VehiculoMapper::toModel);
    }

    @Override
    public VehiculoModel guardar(VehiculoModel modelo) {
        return VehiculoMapper.toModel(repository.save(VehiculoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
