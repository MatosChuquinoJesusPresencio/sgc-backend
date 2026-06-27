package com.condominios.sgc.domain.shared.exception;

public class PisoException extends DominioException {
    private PisoException(String mensaje) {
        super(mensaje);
    }

    public static PisoException numeroRequerido() {
        return new PisoException("numero debe ser positivo");
    }

    public static PisoException torreRequerida() {
        return new PisoException("idTorre no puede ser nulo");
    }

    public static PisoException noEncontrado() {
        return new PisoException("piso no encontrado");
    }
}
