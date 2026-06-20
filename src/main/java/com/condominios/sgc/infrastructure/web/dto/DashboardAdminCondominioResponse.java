package com.condominios.sgc.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardAdminCondominioResponse(
    CondominioInfo condominio,
    long totalTorres,
    long totalPisos,
    long totalApartamentos,
    long totalPropietarios,
    long totalSeguridad,
    long totalEstacionamientos,
    long estacionamientosOcupados,
    long vehiculosEnRecinto,
    long carritosEnUso,
    List<LogAccesoVehicularResponse> accesosRecientes,
    List<LogPrestamoCarritoResponse> prestamosRecientes,
    ConfigData config
) {
    public record CondominioInfo(
        Long id,
        String nombre,
        String pais,
        String ciudad,
        String direccion
    ) {}

    public record ConfigData(
        int maxAutos,
        int maxMotos,
        BigDecimal penalizacionPorMinuto,
        int tiempoMaxPrestamoMin
    ) {}
}
