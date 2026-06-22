package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.domain.model.CiudadModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.CiudadMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.CiudadJpaRepository;

@Component
public class CiudadRepositoryAdapter implements CiudadRepositoryPort {

    private final CiudadJpaRepository repository;

    public CiudadRepositoryAdapter(CiudadJpaRepository repository) {
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

    @Override
    public List<CiudadModel> buscarPorPaisId(Long paisId) {
        return repository.findByIdPais(paisId).stream().map(CiudadMapper::toModel).toList();
    }
}
