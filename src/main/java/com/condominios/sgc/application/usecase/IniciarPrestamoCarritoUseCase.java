package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.IniciarPrestamoRequest;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface IniciarPrestamoCarritoUseCase {
    LogPrestamoCarritoModel ejecutar(IniciarPrestamoRequest request);
}
