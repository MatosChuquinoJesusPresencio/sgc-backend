package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.TorreModel;

public interface ObtenerTorreUseCase {
    TorreModel ejecutar(Long id);
}
