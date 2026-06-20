package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.ConfiguracionModel;

import java.util.Optional;

public interface ConfiguracionRepositoryPort {
    ConfiguracionModel guardar(ConfiguracionModel configuracion);
    Optional<ConfiguracionModel> buscarPorId(Long id);
    Optional<ConfiguracionModel> buscarPorCondominio(Long idCondominio);
}
