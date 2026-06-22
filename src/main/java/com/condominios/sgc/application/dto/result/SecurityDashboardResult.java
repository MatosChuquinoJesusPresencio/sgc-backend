package com.condominios.sgc.application.dto.result;

import java.util.List;

public record SecurityDashboardResult(
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
