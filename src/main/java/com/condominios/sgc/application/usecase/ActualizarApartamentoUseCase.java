package com.condominios.sgc.application.usecase;
import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.domain.model.ApartamentoModel;

public interface ActualizarApartamentoUseCase {
    ApartamentoModel ejecutar(Long id, ActualizarApartamentoRequest request);
}