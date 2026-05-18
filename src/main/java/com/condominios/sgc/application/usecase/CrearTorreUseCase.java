package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.TorreModel;

public interface CrearTorreUseCase {
    TorreModel ejecutar(CrearTorreRequest request);
}
