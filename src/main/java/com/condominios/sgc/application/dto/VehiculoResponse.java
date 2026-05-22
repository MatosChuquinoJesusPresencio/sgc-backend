package com.condominios.sgc.application.dto;

public record VehiculoResponse(
        Long id,
        String marca,
        String color,
        String modelo,
        String placa,
        String propietarioId,
        Long inquilinoId) {
}
