package com.condominios.sgc.infrastructure.web.dto;

import java.util.List;

public record SecurityDashboardResponse(
    int totalEstacionamientos,
    int estacionamientosOcupados,
    int prestamosActivos,
    List<SecurityRecentLogEntry> movimientosRecientes
) {
    public record SecurityRecentLogEntry(
        Long id,
        String tipo,
        String descripcion,
        String fecha
    ) {}
}
