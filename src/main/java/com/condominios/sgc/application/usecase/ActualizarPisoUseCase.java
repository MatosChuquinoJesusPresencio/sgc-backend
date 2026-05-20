package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarPisoRequest;
import com.condominios.sgc.domain.model.PisoModel;

public interface ActualizarPisoUseCase {
    PisoModel ejecutar(Long id, ActualizarPisoRequest request);
}