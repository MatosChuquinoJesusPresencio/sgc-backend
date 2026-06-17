package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ConfiguracionAdapter implements ConfiguracionPort {

    private final ConfiguracionRepository repository;
    private final ConfiguracionMapper mapper;

    public ConfiguracionAdapter(ConfiguracionRepository repository, ConfiguracionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ConfiguracionModel guardar(ConfiguracionModel configuracion) {
        ConfiguracionEntity entidad = mapper.aEntidad(configuracion);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<ConfiguracionModel> obtenerPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).map(mapper::aModelo);
    }
}
