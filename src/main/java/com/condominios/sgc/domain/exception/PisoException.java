package com.condominios.sgc.domain.exception;

public final class PisoException extends RuntimeException {
    private PisoException(String mensaje) {
        super(mensaje);
    }

    public static PisoException pisoNoExistePorId(Long id) {
        return new PisoException("El piso con id " + id + " no existe.");
    }

    public static PisoException pisoYaExistePorId(Long id) {
        return new PisoException("El piso con id " + id + " ya existe.");
    }

    public static PisoException pisoNoExistePorNumero(int numero) {
        return new PisoException("El piso con numero " + numero + " no existe.");
    }

    public static PisoException pisoYaExistePorNumero(int numero) {
        return new PisoException("El piso numero " + numero + " ya existe.");
    }

    public static PisoException numeroInvalido() {
        return new PisoException("El numero de piso debe ser mayor a 0");
    }

    public static PisoException apartamentoObligatorio() {
        return new PisoException("El apartamento es obligatorio");
    }

    public static PisoException apartamentoIdObligatorio() {
        return new PisoException("El id del apartamento es obligatorio");
    }
}
