package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.PisoModel;

public interface ObtenerPisoUseCase {
    PisoModel ejecutar(Long id);
}