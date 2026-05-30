package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.ActualizarConfiguracionRequest;
import com.condominios.sgc.application.usecase.ActualizarConfiguracionUseCase;
import com.condominios.sgc.application.usecase.ObtenerConfiguracionUseCase;
import com.condominios.sgc.domain.model.ConfiguracionModel;

@Service
public class ConfiguracionService {

    private final ObtenerConfiguracionUseCase obtenerConfiguracionUseCase;
    private final ActualizarConfiguracionUseCase actualizarConfiguracionUseCase;

    public ConfiguracionService(
            ObtenerConfiguracionUseCase obtenerConfiguracionUseCase,
            ActualizarConfiguracionUseCase actualizarConfiguracionUseCase) {
        this.obtenerConfiguracionUseCase = obtenerConfiguracionUseCase;
        this.actualizarConfiguracionUseCase = actualizarConfiguracionUseCase;
    }

    public ConfiguracionModel obtener(Long id) {
        return obtenerConfiguracionUseCase.ejecutar(id);
    }

    public ConfiguracionModel actualizar(Long id, ActualizarConfiguracionRequest request) {
        return actualizarConfiguracionUseCase.ejecutar(id, request);
    }
}
