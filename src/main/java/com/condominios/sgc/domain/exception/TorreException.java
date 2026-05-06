package com.condominios.sgc.domain.exception;

public final class TorreException extends RuntimeException {
    private TorreException(String mensaje) {
        super(mensaje);
    }

    public static TorreException torreYaExistePorId(Long torreId) {
        return new TorreException("La torre con id " + torreId + " ya existe en el condominio.");
    }

    public static TorreException torreNoExistePorId(Long torreId) {
        return new TorreException("La torre con id " + torreId + " no existe en el condominio.");
    }

    public static TorreException torreNoExistePorNombre(String nombre) {
        return new TorreException("La torre con nombre " + nombre + " no existe en el condominio.");
    }

    public static TorreException torreYaExistePorNombre(String nombre) {
        return new TorreException("La torre con nombre " + nombre + " ya existe en el condominio.");
    }

    public static TorreException nombreObligatorio() {
        return new TorreException("El nombre de la torre es obligatorio");
    }

    public static TorreException pisoObligatorio() {
        return new TorreException("El piso es obligatorio");
    }

    public static TorreException pisoIdObligatorio() {
        return new TorreException("El id del piso es obligatorio");
    }
}
