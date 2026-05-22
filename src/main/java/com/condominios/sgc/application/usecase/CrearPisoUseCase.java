package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearPisoRequest;
import com.condominios.sgc.domain.model.PisoModel;

public interface CrearPisoUseCase {
    PisoModel ejecutar(CrearPisoRequest request);
}
