package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.util.List;

public record AdminTorreResponse(
    Long id,
    String nombre,
    List<AdminPisoResponse> pisos
) {
}
