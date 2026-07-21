package com.condominios.sgc.application.dto.result;

public record PropietarioVehiculoResult(
    Long id,
    String marca,
    String color,
    String modelo,
    String placa,
    String tipo,
    Long idEstacionamiento,
    String nombreInquilino,
    boolean esDelPropietario
) {
}
