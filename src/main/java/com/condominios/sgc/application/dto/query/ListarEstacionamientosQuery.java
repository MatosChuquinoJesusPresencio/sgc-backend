package com.condominios.sgc.application.dto.query;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;

public record ListarEstacionamientosQuery(
    int pagina,
    int tamano,
    Integer numero,
    TipoVehiculo tipoVehiculo,
    Boolean disponible,
    Long idApartamento,
    Long idCondominio
) {}
