package com.condominios.sgc.application.usecase;

import com.condominios.sgc.infrastructure.web.dto.CondominioRelationsResponse;

public interface ObtenerCondominioRelationsUseCase {
    CondominioRelationsResponse ejecutar(Long condominioId);
}
