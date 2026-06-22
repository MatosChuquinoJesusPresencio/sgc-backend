package com.condominios.sgc.application.dto.result;

public record DashboardMetricsResult(
    long totalCondominios,
    long condominiosActivos,
    long totalAdministradores,
    long totalPropietarios,
    long totalAgentes,
    long totalUsuarios
) {
}
