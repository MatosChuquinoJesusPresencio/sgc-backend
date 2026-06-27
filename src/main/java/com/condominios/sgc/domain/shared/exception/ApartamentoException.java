package com.condominios.sgc.domain.shared.exception;

public class ApartamentoException extends DominioException {
    private ApartamentoException(String mensaje) {
        super(mensaje);
    }

    public static ApartamentoException numeroRequerido() {
        return new ApartamentoException("numero debe ser positivo");
    }

    public static ApartamentoException metrajeRequerido() {
        return new ApartamentoException("metraje no puede ser nulo");
    }

    public static ApartamentoException propietarioRequerido() {
        return new ApartamentoException("idPropietario no puede ser nulo");
    }

    public static ApartamentoException noEncontrado() {
        return new ApartamentoException("apartamento no encontrado");
    }

    public static ApartamentoException yaTienePropietarioAsignado() {
        return new ApartamentoException("el apartamento ya tiene un propietario asignado");
    }
}
