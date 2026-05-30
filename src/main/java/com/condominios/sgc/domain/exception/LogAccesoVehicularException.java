package com.condominios.sgc.domain.exception;

public final class LogAccesoVehicularException extends DominioException {
    private LogAccesoVehicularException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static LogAccesoVehicularException salidaYaRegistrada() {
        return new LogAccesoVehicularException("La salida ya ha sido registrada", TipoError.BAD_REQUEST);
    }

        public static LogAccesoVehicularException noEncontrado(Long id) {
        return new LogAccesoVehicularException("Log de acceso vehicular no encontrado con ID: " + id, TipoError.NOT_FOUND);
    }

    public static LogAccesoVehicularException placaObligatoria() {
        return new LogAccesoVehicularException("La placa del vehículo es obligatoria", TipoError.BAD_REQUEST);
    }

    public static LogAccesoVehicularException ocupanteObligatorio() {
        return new LogAccesoVehicularException("El tipo de ocupante es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogAccesoVehicularException metodoObligatorio() {
        return new LogAccesoVehicularException("El método de entrada es obligatorio", TipoError.BAD_REQUEST);
    }
}
