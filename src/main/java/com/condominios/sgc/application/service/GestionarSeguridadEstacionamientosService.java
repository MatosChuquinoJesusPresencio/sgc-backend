package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult;
import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult.VehicleInfo;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarSeguridadEstacionamientosUseCase;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;

public class GestionarSeguridadEstacionamientosService implements GestionarSeguridadEstacionamientosUseCase {

    private final CondominioIdResolver condominioIdResolver;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;

    public GestionarSeguridadEstacionamientosService(
            CondominioIdResolver condominioIdResolver,
            EstacionamientoRepositoryPort estacionamientoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        this.condominioIdResolver = condominioIdResolver;
        this.estacionamientoRepository = estacionamientoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public List<SecurityParkingSlotResult> listarSlots(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        return estacionamientoRepository.buscarPorCondominio(condominioId, new PaginaQuery(0, Integer.MAX_VALUE))
            .items().stream()
            .map(e -> {
                var cantidadActual = e.getCantidadActual();
                List<VehicleInfo> vehiculos;
                if (cantidadActual == null || cantidadActual <= 0) {
                    vehiculos = List.of();
                } else {
                    vehiculos = vehiculoRepository.buscarPorIdEstacionamiento(e.getId()).stream()
                        .limit(cantidadActual)
                        .map(v -> new VehicleInfo(v.getPlaca().valor(), v.getTipo().name(),
                            v.getMarca(), v.getModelo(), v.getColor()))
                        .toList();
                }
                return new SecurityParkingSlotResult(
                    e.getId(), e.getNumero(), e.getTipoVehiculo(),
                    e.getCapacidadMaxima(), cantidadActual, e.getDisponible(),
                    vehiculos);
            })
            .toList();
    }
}
