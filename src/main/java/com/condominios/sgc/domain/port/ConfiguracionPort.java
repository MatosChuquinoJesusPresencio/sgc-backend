package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.ConfiguracionModel;
import java.util.Optional;

public interface ConfiguracionPort {
    ConfiguracionModel guardar(ConfiguracionModel configuracion);
    Optional<ConfiguracionModel> obtenerPorId(Long id);
    Optional<ConfiguracionModel> obtenerPorCondominio(Long idCondominio);
}

