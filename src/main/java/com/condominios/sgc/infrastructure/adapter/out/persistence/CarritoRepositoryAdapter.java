package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.CarritoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.CarritoJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CarritoRepositoryAdapter implements CarritoRepositoryPort {

    private final CarritoJpaRepository repository;

    public CarritoRepositoryAdapter(CarritoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CarritoModel> buscarPorId(Long id) {
        return repository.findById(id).map(CarritoMapper::toModel);
    }

    @Override
    public CarritoModel guardar(CarritoModel modelo) {
        return CarritoMapper.toModel(repository.save(CarritoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
