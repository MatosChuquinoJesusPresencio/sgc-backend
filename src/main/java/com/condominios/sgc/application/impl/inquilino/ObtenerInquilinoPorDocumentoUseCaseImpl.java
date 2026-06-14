package com.condominios.sgc.application.impl.inquilino;

import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.application.usecase.inquilino.ObtenerInquilinoPorDocumentoUseCase;
import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ObtenerInquilinoPorDocumentoUseCaseImpl implements ObtenerInquilinoPorDocumentoUseCase {
    private final InquilinoPort inquilinoPort;

    public ObtenerInquilinoPorDocumentoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public InquilinoResponse ejecutar(TipoDocumento tipoDocumento, String numeroDocumento) {
        return inquilinoPort.obtenerPorDocumento(tipoDocumento, numeroDocumento)
            .map(InquilinoResponse::desdeModelo)
            .orElseThrow(InquilinoException::noEncontrado);
    }
}
