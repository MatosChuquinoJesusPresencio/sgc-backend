package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.TorreModel;

public interface ActualizarTorreUseCase {
    TorreModel ejecutar(Long id, ActualizarTorreRequest request);
}
