package com.condominios.sgc.application.usecase;
import com.condominios.sgc.domain.model.ApartamentoModel;

public interface ObtenerApartamentoUseCase {
    ApartamentoModel ejecutar(Long id);
}