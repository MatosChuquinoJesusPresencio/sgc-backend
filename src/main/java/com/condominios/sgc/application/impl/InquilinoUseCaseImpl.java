package com.condominios.sgc.application.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.dto.InquilinoResponse;
import com.condominios.sgc.application.usecase.AsignarVehiculoAInquilinoUseCase;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;
import com.condominios.sgc.application.usecase.RemoverVehiculoDeInquilinoUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.InquilinoPort;

public class InquilinoUseCaseImpl implements CrearInquilinoUseCase, ObtenerInquilinoUseCase,
        ListarInquilinosPorApartamentoUseCase, AsignarVehiculoAInquilinoUseCase,
        RemoverVehiculoDeInquilinoUseCase, EliminarInquilinoUseCase {

    private final InquilinoPort inquilinoPort;
    private final ConfiguracionPort configuracionPort;

    public InquilinoUseCaseImpl(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        this.inquilinoPort = inquilinoPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public InquilinoResponse crear(CrearInquilinoRequest request) {
        ConfiguracionModel config = configuracionPort.findById(1L)
                .orElseThrow(ConfiguracionException::noEncontrada);

        Integer inquilinosActuales = inquilinoPort.countByApartamentoId(request.getApartamentoId());
        config.puedoAgregarInquilino(inquilinosActuales);

        InquilinoModel modelo = new InquilinoModel(
                null,
                request.getNombres(),
                request.getApellidos(),
                request.getDni(),
                request.getApartamentoId()
        );

        InquilinoModel guardado = inquilinoPort.save(modelo);
        return mapearAResponse(guardado);
    }

    @Override
    public InquilinoResponse obtenerPorId(Long id) {
        InquilinoModel modelo = inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(id));
        return mapearAResponse(modelo);
    }

    @Override
    public List<InquilinoResponse> listarPorApartamentoId(Long apartamentoId) {
        return inquilinoPort.findByApartamentoId(apartamentoId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void asignarVehiculo(Long inquilinoId, Long vehiculoId) {
        InquilinoModel modelo = inquilinoPort.findById(inquilinoId)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(inquilinoId));
        modelo.asignarVehiculo(vehiculoId);
        inquilinoPort.save(modelo);
    }

    @Override
    public void removerVehiculo(Long inquilinoId) {
        InquilinoModel modelo = inquilinoPort.findById(inquilinoId)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(inquilinoId));
        modelo.removerVehiculo();
        inquilinoPort.save(modelo);
    }

    @Override
    public void eliminar(Long id) {
        InquilinoModel modelo = inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(id));
        inquilinoPort.deleteById(id);
    }

    private InquilinoResponse mapearAResponse(InquilinoModel modelo) {
        return new InquilinoResponse(
                modelo.getId(),
                modelo.getNombres(),
                modelo.getApellidos(),
                modelo.getDni(),
                modelo.getApartamentoId(),
                modelo.getVehiculoId()
        );
    }
}
