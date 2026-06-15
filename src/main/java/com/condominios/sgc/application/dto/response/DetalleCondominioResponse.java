package com.condominios.sgc.application.dto.response;

public record DetalleCondominioResponse(
    CondominioResponse condominio,
    int cantidadTorres,
    int cantidadPisos,
    int cantidadApartamentos,
    int cantidadEstacionamientos,
    int cantidadCarritos,
    int cantidadUsuarios,
    ConfiguracionResponse configuracion
) {}