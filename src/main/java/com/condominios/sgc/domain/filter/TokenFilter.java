package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.TipoToken;

public record TokenFilter(
    TipoToken tipo,
    Boolean usado,
    Long idUsuario
) {}

