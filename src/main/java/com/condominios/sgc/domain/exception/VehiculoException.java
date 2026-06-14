package com.condominios.sgc.domain.exception;

public class VehiculoException extends DominioException {
    private VehiculoException(String mensaje) {
        super(mensaje);
    }

    public static VehiculoException marcaRequerida() {
        return new VehiculoException("marca no puede estar vacío");
    }

    public static VehiculoException colorRequerido() {
        return new VehiculoException("color no puede estar vacío");
    }

    public static VehiculoException modeloRequerido() {
        return new VehiculoException("modelo no puede estar vacío");
    }

    public static VehiculoException placaRequerida() {
        return new VehiculoException("placa no puede estar vacío");
    }

    public static VehiculoException tipoRequerido() {
        return new VehiculoException("tipo de vehículo no puede ser nulo");
    }

    public static VehiculoException propietarioRequerido() {
        return new VehiculoException("idPropietario no puede ser nulo");
    }

    public static VehiculoException inquilinoRequerido() {
        return new VehiculoException("idInquilino no puede ser nulo");
    }

    public static VehiculoException estacionamientoRequerido() {
        return new VehiculoException("idEstacionamiento no puede ser nulo");
    }

    public static VehiculoException noEncontrado() {
        return new VehiculoException("vehículo no encontrado");
    }

    public static VehiculoException limiteAlcanzado() {
        return new VehiculoException("límite de vehiculos alcanzado");
    }

    public static VehiculoException condominioRequerido() {
        return new VehiculoException("idCondominio no puede ser nulo");
    }

    public static VehiculoException limitePropietarioAlcanzado() {
        return new VehiculoException("límite de vehículos por propietario alcanzado");
    }
}
