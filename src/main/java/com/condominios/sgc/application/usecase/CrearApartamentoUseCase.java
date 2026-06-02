package com.condominios.sgc.application.usecase;
import com.condominios.sgc.application.dto.CrearApartamentoRequest;
import com.condominios.sgc.domain.model.ApartamentoModel;

public interface CrearApartamentoUseCase {
    ApartamentoModel ejecutar(CrearApartamentoRequest request);
}