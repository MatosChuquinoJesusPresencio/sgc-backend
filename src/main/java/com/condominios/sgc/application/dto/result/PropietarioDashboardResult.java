package com.condominios.sgc.application.dto.result;

public record PropietarioDashboardResult(
    Long idApartamento,
    Integer numeroApartamento,
    String torreNombre,
    Integer pisoNumero,
    int totalInquilinos,
    int totalVehiculos,
    int totalLogsRecientes
) {
}
