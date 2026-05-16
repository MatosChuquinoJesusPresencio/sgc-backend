package com.condominios.sgc.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;

public class CondominioAdapter implements CondominioPort {

    private final CondominioRepository condominioRepository;

    public CondominioAdapter(CondominioRepository condominioRepository) {
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<CondominioModel> findById(Long id) {
        return condominioRepository.findById(id).map(CondominioMapper::toModel);
    }

    @Override
    public List<CondominioModel> findAll() {
        return condominioRepository.findAll().stream()
                .map(CondominioMapper::toModel)
                .toList();
    }

    @Override
    public CondominioModel save(CondominioModel model) {
        CondominioEntity entity = CondominioMapper.toEntity(model);
        CondominioEntity savedEntity = condominioRepository.save(entity);
        return CondominioMapper.toModel(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        condominioRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return condominioRepository.existsByNombre(nombre);
    }
}
