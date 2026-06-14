package com.condominios.sgc.application.usecase.logprestamocarrito;

import java.math.BigDecimal;

import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;

public interface RegistrarDevolucionLogPrestamoCarritoUseCase {
    LogPrestamoCarritoResponse ejecutar(Long id, BigDecimal penalizacion);
}
