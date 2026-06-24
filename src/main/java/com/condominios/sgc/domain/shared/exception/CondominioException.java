package com.condominios.sgc.domain.shared.exception;

public class CondominioException extends DominioException {
    private CondominioException(String mensaje) {
        super(mensaje);
    }

    public static CondominioException nombreRequerido() {
        return new CondominioException("nombre no puede estar vacío");
    }

    public static CondominioException paisRequerido() {
        return new CondominioException("idPais no puede ser nulo");
    }

    public static CondominioException ciudadRequerido() {
        return new CondominioException("idCiudad no puede ser nulo");
    }

    public static CondominioException direccionRequerida() {
        return new CondominioException("dirección no puede estar vacío");
    }

    public static CondominioException noEncontrado() {
        return new CondominioException("condominio no encontrado");
    }

    public static CondominioException condominioInactivo() {
        return new CondominioException("el condominio está desactivado");
    }

    public static CondominioException nombreYaExiste(String nombre) {
        return new CondominioException("ya existe un condominio con el nombre '" + nombre + "'");
    }
}
