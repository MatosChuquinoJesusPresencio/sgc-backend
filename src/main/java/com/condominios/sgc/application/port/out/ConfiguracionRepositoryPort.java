package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.ConfiguracionModel;

public interface ConfiguracionRepositoryPort {
    Optional<ConfiguracionModel> buscarPorId(Long id);
    ConfiguracionModel guardar(ConfiguracionModel modelo);
}
