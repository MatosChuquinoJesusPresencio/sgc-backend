package com.condominios.sgc.application.dto.result;

import java.util.List;

public record AdminTorreResult(
    Long id,
    String nombre,
    List<AdminPisoResult> pisos
) {
}
