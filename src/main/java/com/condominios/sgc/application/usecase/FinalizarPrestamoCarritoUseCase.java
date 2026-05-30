package com.condominios.sgc.application.usecase;

import java.math.BigDecimal;

import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

public interface FinalizarPrestamoCarritoUseCase {
    LogPrestamoCarritoModel ejecutar(Long logId, BigDecimal penalizacion);
}
