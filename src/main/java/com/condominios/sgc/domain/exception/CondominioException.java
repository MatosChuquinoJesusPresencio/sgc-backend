package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class CondominioException extends DominioException {
    private CondominioException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static CondominioException nombreObligatorio() {
        return new CondominioException("El nombre del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CondominioException paisObligatorio() {
        return new CondominioException("El país es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CondominioException ciudadObligatoria() {
        return new CondominioException("La ciudad es obligatoria", TipoError.BAD_REQUEST);
    }

    public static CondominioException direccionObligatoria() {
        return new CondominioException("La dirección es obligatoria", TipoError.BAD_REQUEST);
    }

    public static CondominioException fechaCreacionObligatoria() {
        return new CondominioException("La fecha de creación es obligatoria", TipoError.BAD_REQUEST);
    }

    public static CondominioException noEncontrado() {
        return new CondominioException("El condominio solicitado no existe", TipoError.NOT_FOUND);
    }

    public static CondominioException nombreEnUso() {
        return new CondominioException("El nombre del condominio ya está en uso", TipoError.BAD_REQUEST);
    }
}
