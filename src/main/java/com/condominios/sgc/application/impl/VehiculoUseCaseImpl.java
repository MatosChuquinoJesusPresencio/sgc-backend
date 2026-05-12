package com.condominios.sgc.application.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class VehiculoUseCaseImpl implements CrearVehiculoUseCase, ObtenerVehiculoUseCase,
        ListarVehiculosUseCase, EliminarVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public VehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse crear(CrearVehiculoRequest request) {
        VehiculoModel modelo = new VehiculoModel(
                null,
                request.getMarca(),
                request.getColor(),
                request.getModelo(),
                request.getPlaca()
        );

        VehiculoModel guardado = vehiculoPort.save(modelo);
        return mapearAResponse(guardado);
    }

    @Override
    public VehiculoResponse obtenerPorId(Long id) {
        VehiculoModel modelo = vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.vehiculoNoExistePorId(id));
        return mapearAResponse(modelo);
    }

    @Override
    public List<VehiculoResponse> listarTodos() {
        return vehiculoPort.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        VehiculoModel modelo = vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.vehiculoNoExistePorId(id));
        vehiculoPort.deleteById(id);
    }

    private VehiculoResponse mapearAResponse(VehiculoModel modelo) {
        return new VehiculoResponse(
                modelo.getId(),
                modelo.getMarca(),
                modelo.getColor(),
                modelo.getModelo(),
                modelo.getPlaca(),
                modelo.getPropietarioUsuarioId(),
                modelo.getPropietarioInquilinoId()
        );
    }
}
