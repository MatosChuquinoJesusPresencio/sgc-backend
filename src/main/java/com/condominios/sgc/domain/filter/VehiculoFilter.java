package com.condominios.sgc.domain.filter;

public record VehiculoFilter(
    String placa, 
    Long idPropietario, 
    Long idInquilino, 
    String marca
) {}

