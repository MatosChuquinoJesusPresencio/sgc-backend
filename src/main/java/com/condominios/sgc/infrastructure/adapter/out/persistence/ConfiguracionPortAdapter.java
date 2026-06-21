package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.ConfiguracionRepositoryPort;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.ConfiguracionJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ConfiguracionPortAdapter implements ConfiguracionRepositoryPort {

    private final ConfiguracionJpaRepository repository;

    public ConfiguracionPortAdapter(ConfiguracionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConfiguracionModel> buscarPorId(Long id) {
        return repository.findById(id).map(ConfiguracionMapper::toModel);
    }

    @Override
    public ConfiguracionModel guardar(ConfiguracionModel modelo) {
        return ConfiguracionMapper.toModel(repository.save(ConfiguracionMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
