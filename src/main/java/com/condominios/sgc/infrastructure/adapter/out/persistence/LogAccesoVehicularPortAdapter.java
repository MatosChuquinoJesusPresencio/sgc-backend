package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.LogAccesoVehicularJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class LogAccesoVehicularPortAdapter implements LogAccesoVehicularRepositoryPort {

    private final LogAccesoVehicularJpaRepository repository;

    public LogAccesoVehicularPortAdapter(LogAccesoVehicularJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<LogAccesoVehicularModel> buscarPorId(Long id) {
        return repository.findById(id).map(LogAccesoVehicularMapper::toModel);
    }

    @Override
    public LogAccesoVehicularModel guardar(LogAccesoVehicularModel modelo) {
        return LogAccesoVehicularMapper.toModel(repository.save(LogAccesoVehicularMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
