package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearEstacionamientoRequest;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface CrearEstacionamientoUseCase {
    EstacionamientoModel ejecutar(CrearEstacionamientoRequest request);
}
