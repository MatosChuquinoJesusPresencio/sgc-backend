package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.infrastructure.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConfiguracionAdapter implements ConfiguracionPort {

    private final ConfiguracionRepository configuracionRepository;
    private final CondominioRepository condominioRepository;

    public ConfiguracionAdapter(ConfiguracionRepository configuracionRepository,
                                CondominioRepository condominioRepository) {
        this.configuracionRepository = configuracionRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<ConfiguracionModel> findById(Long id) {
        return configuracionRepository.findById(id).map(ConfiguracionMapper::toModel);
    }

    @Override
    public Optional<ConfiguracionModel> findByCondominioId(Long condominioId) {
        return configuracionRepository.findByCondominioId(condominioId).map(ConfiguracionMapper::toModel);
    }

    @Override
    public Optional<ConfiguracionModel> findByApartamentoId(Long apartamentoId) {
        return configuracionRepository.findByApartamentoId(apartamentoId)
            .map(ConfiguracionMapper::toModel);
    }

    @Override
    public ConfiguracionModel save(ConfiguracionModel model) {
        var entity = ConfiguracionMapper.toEntity(model);
        entity.setCondominio(condominioRepository.findById(model.getCondominioId())
            .orElseThrow(() -> CondominioException.noEncontrado(model.getCondominioId())));
        var saved = configuracionRepository.save(entity);
        return ConfiguracionMapper.toModel(saved);
    }
}
