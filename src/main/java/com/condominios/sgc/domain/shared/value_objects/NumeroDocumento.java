package com.condominios.sgc.domain.shared.value_objects;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;
import com.condominios.sgc.domain.type.TipoDocumento;

public record NumeroDocumento(TipoDocumento tipo, String numero) {
    public NumeroDocumento {
        if (tipo == null)
            throw ValueObjectException.tipoDocumentoRequerido();
        if (numero == null || numero.isBlank())
            throw ValueObjectException.documentoRequerido();
    }
}
