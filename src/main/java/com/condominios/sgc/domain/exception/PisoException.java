package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class PisoException extends DominioException {
    private PisoException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static PisoException numeroInvalido() {
        return new PisoException("El numero de piso debe ser mayor a 0", TipoError.BAD_REQUEST);
    }

    public static PisoException torreIdObligatorio() {
        return new PisoException("El id de la torre es obligatorio", TipoError.BAD_REQUEST);
    }
}
