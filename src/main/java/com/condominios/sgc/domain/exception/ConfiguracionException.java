package com.condominios.sgc.domain.exception;

public final class ConfiguracionException extends DominioException {
    private ConfiguracionException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static ConfiguracionException vehiculosPorTipoExcedidos(String tipo, int max, int actual) {
        return new ConfiguracionException("Límite de " + tipo + " excedido. Máx: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException vehiculosPorPropietarioExcedidos(int max, int actual) {
        return new ConfiguracionException("Límite de vehículos por propietario excedido. Máx: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException tiempoPrestamoExcedido(int max, int actual) {
        return new ConfiguracionException("Tiempo de préstamo excedido. Máx: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException tipoVehiculoNoSoportado(String tipo) {
        return new ConfiguracionException("Tipo de vehículo no soportado: " + tipo, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException estacionamientosExcedidosPorApartamento(int max, int actual) {
        return new ConfiguracionException("Límite de estacionamientos por apartamento excedido. Máx: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException carritosExcedidosPorApartamento(int max, int actual) {
        return new ConfiguracionException("Límite de carritos por apartamento excedido. Máx: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException inquilinosPorApartamentoExcedidos(int max, int actual) {
        return new ConfiguracionException("Límite de inquilinos por apartamento excedido. Máx: " + max + ", Actual: " + actual, TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoAutosInvalido() {
        return new ConfiguracionException("El número máximo de autos no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoMotosInvalido() {
        return new ConfiguracionException("El número máximo de motos no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException penalizacionInvalida() {
        return new ConfiguracionException("La penalización por minuto no puede ser nula o negativa", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoTiempoPrestamoInvalido() {
        return new ConfiguracionException("El tiempo máximo de préstamo no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoEstacionamientosInvalido() {
        return new ConfiguracionException("El número máximo de estacionamientos por apartamento no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoCarritosInvalido() {
        return new ConfiguracionException("El número máximo de carritos por apartamento no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoVehiculosPorPropietarioInvalido() {
        return new ConfiguracionException("El número máximo de vehículos por propietario no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException maximoInquilinosInvalido() {
        return new ConfiguracionException("El número máximo de inquilinos por apartamento no puede ser nulo o negativo", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException condominioIdObligatorio() {
        return new ConfiguracionException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static ConfiguracionException noEncontrada() {
        return new ConfiguracionException("La configuración solicitada no existe", TipoError.NOT_FOUND);
    }
}
