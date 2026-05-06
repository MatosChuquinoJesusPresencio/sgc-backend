package com.condominios.sgc.domain.exception;

public final class LogAccesoVehicularException extends RuntimeException {
    private LogAccesoVehicularException(String mensaje) {
        super(mensaje);
    }

    public static LogAccesoVehicularException salidaYaRegistrada() {
        return new LogAccesoVehicularException("La salida ya ha sido registrada");
    }

    public static LogAccesoVehicularException logNoExistePorId(Long id) {
        return new LogAccesoVehicularException("El log de acceso con id " + id + " no existe");
    }

    public static LogAccesoVehicularException placaObligatoria() {
        return new LogAccesoVehicularException("La placa del vehiculo es obligatoria");
    }

    public static LogAccesoVehicularException ocupanteObligatorio() {
        return new LogAccesoVehicularException("El tipo de ocupante es obligatorio");
    }

    public static LogAccesoVehicularException metodoObligatorio() {
        return new LogAccesoVehicularException("El metodo de entrada es obligatorio");
    }
}
