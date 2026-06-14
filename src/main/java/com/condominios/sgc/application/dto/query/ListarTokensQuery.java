package com.condominios.sgc.application.dto.query;

import com.condominios.sgc.domain.auxiliar.TipoToken;

public record ListarTokensQuery(
    int pagina,
    int tamano,
    TipoToken tipo,
    Boolean usado,
    Long idUsuario
) {}
