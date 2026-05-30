package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public class GenericoException extends DominioException {
    private GenericoException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static GenericoException argumentoDuplicado(String mensaje) {
        return new GenericoException(mensaje, TipoError.BAD_REQUEST);
    }

    public static GenericoException argumentoObligatorio(String mensaje) {
        return new GenericoException(mensaje, TipoError.BAD_REQUEST);
    }

    public static GenericoException argumentoInvalido(String mensaje) {
        return new GenericoException(mensaje, TipoError.BAD_REQUEST);
    }
}
