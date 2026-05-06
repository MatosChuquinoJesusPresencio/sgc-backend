package com.condominios.sgc.domain.exception;

public class GenericoException extends RuntimeException {

    private GenericoException(String mensaje) {
        super(mensaje);
    }

    public static GenericoException argumentoDuplicado(String mensaje) {
        return new GenericoException(mensaje);
    }

    public static GenericoException argumentoObligatorio(String mensaje) {
        return new GenericoException(mensaje);
    }

    public static GenericoException argumentoInvalido(String mensaje) {
        return new GenericoException(mensaje);
    }
}
