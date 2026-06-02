package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarEstacionamientoRequest;
import com.condominios.sgc.domain.model.EstacionamientoModel;

public interface ActualizarEstacionamientoUseCase {
    EstacionamientoModel ejecutar(Long id, ActualizarEstacionamientoRequest request);
}
