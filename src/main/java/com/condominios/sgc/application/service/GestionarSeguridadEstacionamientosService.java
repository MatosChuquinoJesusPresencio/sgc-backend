package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarSeguridadEstacionamientosUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;

public class GestionarSeguridadEstacionamientosService implements GestionarSeguridadEstacionamientosUseCase {

    private final CondominioIdResolver condominioIdResolver;
    private final EstacionamientoRepositoryPort estacionamientoRepository;

    public GestionarSeguridadEstacionamientosService(
            CondominioIdResolver condominioIdResolver,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        this.condominioIdResolver = condominioIdResolver;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public List<SecurityParkingSlotResult> listarSlots(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        return estacionamientoRepository.buscarPorCondominio(condominioId, new PaginaQuery(0, Integer.MAX_VALUE))
            .items().stream()
            .map(e -> new SecurityParkingSlotResult(
                e.getId(), e.getNumero(), e.getTipoVehiculo(),
                e.getCapacidadMaxima(), e.getCantidadActual(), e.getDisponible()))
            .toList();
    }
}
