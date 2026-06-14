package com.condominios.sgc.application.dto.query;

public record ListarVehiculosQuery(
    int pagina,
    int tamano,
    String placa,
    Long idPropietario,
    Long idInquilino,
    String marca
) {}
