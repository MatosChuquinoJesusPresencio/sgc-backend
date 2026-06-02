package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.CondominioModel;

public interface ObtenerCondominioUseCase {
    CondominioModel ejecutar(Long id);
}
