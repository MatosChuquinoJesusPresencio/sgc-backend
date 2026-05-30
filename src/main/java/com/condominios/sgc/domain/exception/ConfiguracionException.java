package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class ConfiguracionException extends DominioException {
    private ConfiguracionException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static ConfiguracionException vehiculosPorTipoExcedidos(String tipo, int max, int actual) {
        return new ConfiguracionException("Limite de " + tipo + " excedido. Max: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException vehiculosPorPropietarioExcedidos(int max, int actual) {
        return new ConfiguracionException("Limite de vehiculos por propietario excedido. Max: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException tiempoPrestamoExcedido(int max, int actual) {
        return new ConfiguracionException("Tiempo de prestamo excedido. Max: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException tipoVehiculoNoSoportado(String tipo) {
        return new ConfiguracionException("Tipo de vehiculo no soportado: " + tipo, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException estacionamientosExcedidosPorApartamento(int max, int actual) {
        return new ConfiguracionException("Limite de estacionamientos por apartamento excedido. Max: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException carritosExcedidosPorApartamento(int max, int actual) {
        return new ConfiguracionException("Limite de carritos por apartamento excedido. Max: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException inquilinosPorApartamentoExcedidos(int max, int actual) {
        return new ConfiguracionException("Limite de inquilinos por apartamento excedido. Max: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maxAutosInvalido() {
        return new ConfiguracionException("El numero maximo de autos no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maxMotosInvalido() {
        return new ConfiguracionException("El numero maximo de motos no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException penalizacionInvalida() {
        return new ConfiguracionException("La penalizacion por minuto no puede ser nula o negativa", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException tiempoPrestamoInvalido() {
        return new ConfiguracionException("El tiempo maximo de prestamo no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maxEstacionamientosInvalido() {
        return new ConfiguracionException("El numero maximo de estacionamientos por apartamento no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maxCarritosInvalido() {
        return new ConfiguracionException("El numero maximo de carritos por apartamento no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maxVehiculosPorPropietarioInvalido() {
        return new ConfiguracionException("El numero maximo de vehiculos por propietario no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maxInquilinosInvalido() {
        return new ConfiguracionException("El numero maximo de inquilinos por apartamento no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException noEncontrada() {
        return new ConfiguracionException("La configuracion solicitada no existe", TipoError.NOT_FOUND);
    }

    public static ConfiguracionException condominioIdObligatorio() {
        return new ConfiguracionException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }
}
