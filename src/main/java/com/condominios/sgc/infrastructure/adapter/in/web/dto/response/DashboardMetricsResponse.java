package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record DashboardMetricsResponse(
    long totalCondominios,
    long condominiosActivos,
    long totalAdministradores,
    long totalPropietarios,
    long totalAgentes,
    long totalUsuarios
) {
}
