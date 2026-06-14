package com.condominios.sgc.application.dto.query;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;

public record ListarInquilinosQuery(
    int pagina,
    int tamano,
    String nombres,
    String apellidos,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    Long idApartamento
) {}
