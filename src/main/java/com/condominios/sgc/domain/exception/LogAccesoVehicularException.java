package com.condominios.sgc.domain.exception;

public class LogAccesoVehicularException extends DominioException {
    private LogAccesoVehicularException(String mensaje) {
        super(mensaje);
    }

    private LogAccesoVehicularException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static LogAccesoVehicularException placaRequerida() {
        return new LogAccesoVehicularException("placa no puede estar vacío");
    }

    public static LogAccesoVehicularException ocupanteRequerido() {
        return new LogAccesoVehicularException("ocupante no puede ser nulo");
    }

    public static LogAccesoVehicularException metodoRequerido() {
        return new LogAccesoVehicularException("metodo de entrada no puede ser nulo");
    }

    public static LogAccesoVehicularException vehiculoRequerido() {
        return new LogAccesoVehicularException("idVehiculo no puede ser nulo");
    }

    public static LogAccesoVehicularException estacionamientoRequerido() {
        return new LogAccesoVehicularException("idEstacionamiento no puede ser nulo");
    }

    public static LogAccesoVehicularException salidaYaRegistrada() {
        return new LogAccesoVehicularException("la salida ya fue registrada");
    }

    public static LogAccesoVehicularException noEncontrado() {
        return new LogAccesoVehicularException("log de acceso vehicular no encontrado", 404);
    }
}
