package com.condominios.sgc.infrastructure.adapter;

import java.util.Optional;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.infrastructure.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;

public class ConfiguracionAdapter implements ConfiguracionPort {

    public final ConfiguracionRepository repository;
    private final CondominioRepository condominioRepository;

    public ConfiguracionAdapter(ConfiguracionRepository repository, CondominioRepository condominioRepository) {
        this.repository = repository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<ConfiguracionModel> findById(Long id) {
        return repository.findById(id).map(ConfiguracionMapper::toModel);
    }

    @Override
    public ConfiguracionModel save(ConfiguracionModel model) {
        var entity = ConfiguracionMapper.toEntity(model);
        if (model.getCondominioId() != null) {
            entity.setCondominio(condominioRepository.getReferenceById(model.getCondominioId()));
        }
        var saved = repository.save(entity);
        return ConfiguracionMapper.toModel(saved);
    }
}
