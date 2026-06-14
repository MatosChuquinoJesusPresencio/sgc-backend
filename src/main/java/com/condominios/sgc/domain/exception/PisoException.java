package com.condominios.sgc.domain.exception;

public class PisoException extends DominioException {
    private PisoException(String mensaje) {
        super(mensaje);
    }

    public static PisoException numeroRequerido() {
        return new PisoException("numero debe ser un valor positivo");
    }

    public static PisoException noEncontrado() {
        return new PisoException("piso no encontrado");
    }
}
