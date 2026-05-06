package com.condominios.sgc.domain.exception;

public final class ApartamentoException extends RuntimeException {
    private ApartamentoException(String mensaje) {
        super(mensaje);
    }

    public static ApartamentoException datosObligatorios() {
        return new ApartamentoException("El numero y metraje son obligatorios");
    }

    public static ApartamentoException numeroInvalido() {
        return new ApartamentoException("El numero de apartamento debe ser mayor a 0");
    }

    public static ApartamentoException metrajeInvalido() {
        return new ApartamentoException("El metraje debe ser mayor a 0");
    }

    public static ApartamentoException pisoIdObligatorio() {
        return new ApartamentoException("El id del piso es obligatorio");
    }

    public static ApartamentoException apartamentoNoExistePorId(Long id) {
        return new ApartamentoException("El apartamento con id " + id + " no existe");
    }

    public static ApartamentoException apartamentoNoExistePorNumero(Integer numero) {
        return new ApartamentoException("El apartamento con numero " + numero + " no existe");
    }

    public static ApartamentoException apartamentoYaExistePorId(Long id) {
        return new ApartamentoException("El apartamento con id " + id + " ya existe");
    }

    public static ApartamentoException apartamentoYaExistePorNumero(Integer numero) {
        return new ApartamentoException("El apartamento con numero " + numero + " ya existe");
    }
}
