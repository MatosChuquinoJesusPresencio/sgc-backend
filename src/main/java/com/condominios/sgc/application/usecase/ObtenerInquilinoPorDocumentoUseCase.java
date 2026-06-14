package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.domain.auxiliar.TipoDocumento;

public interface ObtenerInquilinoPorDocumentoUseCase {
    InquilinoResponse ejecutar(TipoDocumento tipoDocumento, String numeroDocumento);
}
