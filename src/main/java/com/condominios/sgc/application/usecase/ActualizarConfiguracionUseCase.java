package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarConfiguracionRequest;
import com.condominios.sgc.domain.model.ConfiguracionModel;

public interface ActualizarConfiguracionUseCase {
    ConfiguracionModel ejecutar(Long id, ActualizarConfiguracionRequest request);
}
