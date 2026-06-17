package com.condominios.sgc.domain.exception;

public class PisoException extends DominioException {
    private PisoException(String mensaje) {
        super(mensaje);
    }

    private PisoException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static PisoException numeroRequerido() {
        return new PisoException("numero debe ser un valor positivo");
    }

    public static PisoException torreRequerida() {
        return new PisoException("idTorre no puede ser nulo");
    }

    public static PisoException noEncontrado() {
        return new PisoException("piso no encontrado", 404);
    }
}
