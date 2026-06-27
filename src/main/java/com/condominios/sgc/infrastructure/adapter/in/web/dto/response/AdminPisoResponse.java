package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.util.List;

public record AdminPisoResponse(
    Long id,
    Integer numero,
    List<AdminApartamentoResponse> apartamentos
) {
}
