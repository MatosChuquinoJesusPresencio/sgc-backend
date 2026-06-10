package com.condominios.sgc.web.dto;

import java.math.BigDecimal;

public record CondominioRelationsResponse(
    long torres,
    long usuarios,
    long carritos,
    long configuraciones,
    ConfigData config
) {
    public record ConfigData(
        int maxAutos,
        int maxMotos,
        BigDecimal penalizacionPorMinuto,
        int tiempoMaxPrestamoMin
    ) {}
}
