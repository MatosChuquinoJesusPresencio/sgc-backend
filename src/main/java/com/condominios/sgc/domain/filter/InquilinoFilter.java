package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;

public record InquilinoFilter(
    String nombres,
    String apellidos,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    Long idApartamento
) {}

