package com.condominios.sgc.domain.exception;

public class EstacionamientoException extends DominioException {
    private EstacionamientoException(String mensaje) {
        super(mensaje);
    }

    private EstacionamientoException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static EstacionamientoException numeroRequerido() {
        return new EstacionamientoException("numero debe ser un valor positivo");
    }

    public static EstacionamientoException tipoVehiculoRequerido() {
        return new EstacionamientoException("tipoVehiculo no puede ser nulo");
    }

    public static EstacionamientoException capacidadRequerida() {
        return new EstacionamientoException("capacidadMaxima debe ser un valor positivo");
    }

    public static EstacionamientoException apartamentoRequerido() {
        return new EstacionamientoException("idApartamento no puede ser nulo");
    }

    public static EstacionamientoException sinEspacio() {
        return new EstacionamientoException("no hay espacio disponible en el estacionamiento");
    }

    public static EstacionamientoException condominioRequerido() {
        return new EstacionamientoException("idCondominio no puede ser nulo");
    }

    public static EstacionamientoException noEncontrado() {
        return new EstacionamientoException("estacionamiento no encontrado", 404);
    }
}
