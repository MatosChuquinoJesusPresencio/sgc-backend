package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class LogAccesoVehicularException extends DominioException {
    private LogAccesoVehicularException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static LogAccesoVehicularException salidaYaRegistrada() {
        return new LogAccesoVehicularException("La salida ya ha sido registrada", TipoError.BAD_REQUEST);
    }

    public static LogAccesoVehicularException logNoExistePorId(Long id) {
        return new LogAccesoVehicularException("El log de acceso con id " + id + " no existe", TipoError.NOT_FOUND);
    }

    public static LogAccesoVehicularException placaObligatoria() {
        return new LogAccesoVehicularException("La placa del vehiculo es obligatoria", TipoError.BAD_REQUEST);
    }

    public static LogAccesoVehicularException ocupanteObligatorio() {
        return new LogAccesoVehicularException("El tipo de ocupante es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogAccesoVehicularException metodoObligatorio() {
        return new LogAccesoVehicularException("El metodo de entrada es obligatorio", TipoError.BAD_REQUEST);
    }
}
