package com.condominios.sgc.domain.exception;

public final class CondominioException extends RuntimeException {
    private CondominioException(String mensaje) {
        super(mensaje);
    }

    public static CondominioException sinConfiguracion() {
        return new CondominioException("El condominio debe tener una configuración asignada.");
    }

    public static CondominioException nombreObligatorio() {
        return new CondominioException("El nombre del condominio es obligatorio");
    }

    public static CondominioException paisObligatorio() {
        return new CondominioException("El país es obligatorio");
    }

    public static CondominioException ciudadObligatoria() {
        return new CondominioException("La ciudad es obligatoria");
    }

    public static CondominioException direccionObligatoria() {
        return new CondominioException("La dirección es obligatoria");
    }

    public static CondominioException fechaCreacionObligatoria() {
        return new CondominioException("La fecha de creación es obligatoria");
    }

    public static CondominioException torreObligatoria() {
        return new CondominioException("La torre es obligatoria");
    }

    public static CondominioException torreIdObligatorio() {
        return new CondominioException("El id de la torre es obligatorio");
    }

    public static CondominioException estacionamientoObligatorio() {
        return new CondominioException("El estacionamiento es obligatorio");
    }

    public static CondominioException estacionamientoIdObligatorio() {
        return new CondominioException("El id del estacionamiento es obligatorio");
    }

    public static CondominioException carritoObligatorio() {
        return new CondominioException("El carrito es obligatorio");
    }

    public static CondominioException carritoIdObligatorio() {
        return new CondominioException("El id del carrito es obligatorio");
    }

    public static CondominioException configuracionIdObligatorio() {
        return new CondominioException("El id de la configuración es obligatorio");
    }

    public static CondominioException nombreEnUso() {
        return new CondominioException("El nombre ingresado esta en uso");
    }

    public static CondominioException noEncontrado() {
        return new CondominioException("El condominio no fue encontrado");
    }
}
