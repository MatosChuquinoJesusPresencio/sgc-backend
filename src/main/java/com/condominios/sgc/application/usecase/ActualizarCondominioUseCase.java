package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarCondominioRequest;
import com.condominios.sgc.domain.model.CondominioModel;

public interface ActualizarCondominioUseCase {
    CondominioModel ejecutar(Long id, ActualizarCondominioRequest request);
}
