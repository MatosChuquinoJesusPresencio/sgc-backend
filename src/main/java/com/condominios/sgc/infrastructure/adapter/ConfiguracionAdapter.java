package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.ConfiguracionMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConfiguracionAdapter implements ConfiguracionPort {

    private final ConfiguracionRepository configuracionRepository;
    private final CondominioRepository condominioRepository;
    private final ApartamentoRepository apartamentoRepository;

    public ConfiguracionAdapter(ConfiguracionRepository configuracionRepository,
                                CondominioRepository condominioRepository,
                                ApartamentoRepository apartamentoRepository) {
        this.configuracionRepository = configuracionRepository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
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
        return apartamentoRepository.findById(apartamentoId)
            .map(ApartamentoEntity::getPiso)
            .map(piso -> piso.getTorre().getCondominio())
            .map(condominio -> condominio.getConfiguracion())
            .map(ConfiguracionMapper::toModel);
    }

    @Override
    public ConfiguracionModel save(ConfiguracionModel model) {
        var entity = ConfiguracionMapper.toEntity(model);
        entity.setCondominio(condominioRepository.findById(model.getCondominioId())
            .orElseThrow(() -> new RuntimeException("Condominio no encontrado: " + model.getCondominioId())));
        var saved = configuracionRepository.save(entity);
        return ConfiguracionMapper.toModel(saved);
    }
}
