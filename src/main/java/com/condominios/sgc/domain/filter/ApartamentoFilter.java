package com.condominios.sgc.domain.filter;

public record ApartamentoFilter(
    Integer numero,
    Long idPiso,
    Long idPropietario,
    Boolean derechoEstacionamiento
) {}

