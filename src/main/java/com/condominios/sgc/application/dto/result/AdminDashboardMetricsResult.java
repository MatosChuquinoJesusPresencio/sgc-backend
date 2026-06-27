package com.condominios.sgc.application.dto.result;

public record AdminDashboardMetricsResult(
    long totalTorres,
    long totalPisos,
    long totalApartamentos,
    long totalPropietarios,
    long totalAgentes,
    long totalVehiculos,
    long totalCarritos
) {
}
