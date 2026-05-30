package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class AutenticacionException extends DominioException {
    private AutenticacionException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static AutenticacionException credencialesInvalidas() {
        return new AutenticacionException("Credenciales inválidas", TipoError.BAD_REQUEST);
    }

    public static AutenticacionException usuarioNoRegistrado() {
        return new AutenticacionException("Usuario no registrado en el sistema", TipoError.NOT_FOUND);
    }

    public static AutenticacionException errorCreacion(String detalle) {
        return new AutenticacionException("Error al crear usuario: " + detalle, TipoError.BAD_REQUEST);
    }

    public static AutenticacionException errorAutenticacion(String mensaje) {
        return new AutenticacionException(mensaje, TipoError.BAD_REQUEST);
    }
}
