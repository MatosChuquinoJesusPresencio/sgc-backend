package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record EstacionamientoFilter(
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Boolean disponible,
    Long idApartamento,
    Long idCondominio
) {}

