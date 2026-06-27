package com.condominios.sgc.infrastructure.adapter.in.web.dto.response;

import java.util.List;

public record AdminStructureResponse(
    Long condominioId,
    String condominioNombre,
    List<AdminTorreResponse> torres
) {
}
