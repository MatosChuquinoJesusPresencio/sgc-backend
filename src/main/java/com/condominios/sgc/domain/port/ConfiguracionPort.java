package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.model.ConfiguracionModel;

public interface ConfiguracionPort {
    Optional<ConfiguracionModel> findById(Long id);
    Optional<ConfiguracionModel> findByCondominioId(Long condominioId);
    Optional<ConfiguracionModel> findByApartamentoId(Long apartamentoId);
    ConfiguracionModel save(ConfiguracionModel model);
}
