package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record AdminApartamentoDetailResponse(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    Long idPropietario,
    String nombrePropietario,
    String torreNombre,
    Integer pisoNumero,
    List<AdminInquilinoResponse> inquilinos
) {
}
