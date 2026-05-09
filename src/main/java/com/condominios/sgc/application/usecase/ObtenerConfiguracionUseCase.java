package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.ConfiguracionModel;

public interface ObtenerConfiguracionUseCase {
    ConfiguracionModel ejecutar(Long id);
}
