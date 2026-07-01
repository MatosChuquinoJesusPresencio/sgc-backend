package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.ConfiguracionRepositoryPort;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.shared.exception.ConfiguracionException;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.ConfiguracionJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ConfiguracionRepositoryAdapter implements ConfiguracionRepositoryPort {

    private final ConfiguracionJpaRepository repository;

    public ConfiguracionRepositoryAdapter(ConfiguracionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConfiguracionModel> buscarPorId(Long id) {
        return repository.findById(id).map(ConfiguracionMapper::toModel);
    }

    @Override
    public Optional<ConfiguracionModel> buscarPorCondominioId(Long idCondominio) {
        return repository.findByCondominioId(idCondominio).map(ConfiguracionMapper::toModel);
    }

    @Override
    public ConfiguracionModel guardar(ConfiguracionModel modelo) {
        if (modelo.getId() != null) {
            var entity = repository.findById(modelo.getId())
                .orElseThrow(ConfiguracionException::noEncontrado);
            ConfiguracionMapper.applyToEntity(modelo, entity);
            return ConfiguracionMapper.toModel(repository.save(entity));
        }
        return ConfiguracionMapper.toModel(repository.save(ConfiguracionMapper.toEntity(modelo)));
    }
}
