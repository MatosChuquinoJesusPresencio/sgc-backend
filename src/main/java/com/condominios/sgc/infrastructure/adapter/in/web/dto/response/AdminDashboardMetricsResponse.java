package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

public record AdminDashboardMetricsResponse(
    long totalTorres,
    long totalPisos,
    long totalApartamentos,
    long totalPropietarios,
    long totalAgentes,
    long totalVehiculos,
    long totalCarritos
) {
}
