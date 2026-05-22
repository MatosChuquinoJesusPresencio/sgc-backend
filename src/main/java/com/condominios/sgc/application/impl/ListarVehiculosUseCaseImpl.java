package com.condominios.sgc.application.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ListarVehiculosUseCaseImpl implements ListarVehiculosUseCase {

    private final VehiculoPort vehiculoPort;

    public ListarVehiculosUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public List<VehiculoResponse> listarTodos() {
        return vehiculoPort.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private VehiculoResponse mapearAResponse(VehiculoModel modelo) {
        return new VehiculoResponse(
                modelo.getId(),
                modelo.getMarca(),
                modelo.getColor(),
                modelo.getModelo(),
                modelo.getPlaca(),
                modelo.getPropietarioId(),
                modelo.getInquilinoId());
    }
}
