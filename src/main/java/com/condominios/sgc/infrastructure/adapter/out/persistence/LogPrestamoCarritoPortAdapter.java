package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.LogPrestamoCarritoJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class LogPrestamoCarritoPortAdapter implements LogPrestamoCarritoRepositoryPort {

    private final LogPrestamoCarritoJpaRepository repository;

    public LogPrestamoCarritoPortAdapter(LogPrestamoCarritoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<LogPrestamoCarritoModel> buscarPorId(Long id) {
        return repository.findById(id).map(LogPrestamoCarritoMapper::toModel);
    }

    @Override
    public LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel modelo) {
        return LogPrestamoCarritoMapper.toModel(repository.save(LogPrestamoCarritoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
