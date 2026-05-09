package com.condominios.sgc.infrastructure.adapter;

import java.util.Optional;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.infrastructure.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;

public class ConfiguracionAdapeter implements ConfiguracionPort {

    public final ConfiguracionRepository repository;

    public ConfiguracionAdapeter(ConfiguracionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConfiguracionModel> findById(Long id) {
        return repository.findById(id).map(ConfiguracionMapper::toModel);
    }

    @Override
    public ConfiguracionModel save(ConfiguracionModel model) {
        var entity = ConfiguracionMapper.toEntity(model);
        return ConfiguracionMapper.toModel(entity);
    }
    
}
