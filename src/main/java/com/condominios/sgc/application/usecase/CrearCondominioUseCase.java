package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearCondominioRequest;
import com.condominios.sgc.domain.model.CondominioModel;

public interface CrearCondominioUseCase {
    CondominioModel ejecutar(CrearCondominioRequest request);
}
