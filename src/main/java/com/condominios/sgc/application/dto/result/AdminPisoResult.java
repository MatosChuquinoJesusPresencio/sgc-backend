package com.condominios.sgc.application.dto.result;

import java.util.List;

public record AdminPisoResult(
    Long id,
    Integer numero,
    List<AdminApartamentoResult> apartamentos
) {
}
