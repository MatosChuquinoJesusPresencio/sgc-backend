package com.condominios.sgc.application.dto.result;

import java.util.List;

public record AdminStructureResult(
    Long condominioId,
    String condominioNombre,
    List<AdminTorreResult> torres
) {
}
