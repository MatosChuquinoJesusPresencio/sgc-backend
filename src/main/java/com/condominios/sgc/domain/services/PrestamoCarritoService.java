package com.condominios.sgc.domain.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import com.condominios.sgc.domain.type.EstadoCarrito;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.shared.exception.LogPrestamoCarritoException;

public class PrestamoCarritoService {

    public LogPrestamoCarritoModel prestar(
            CarritoModel carrito,
            String nombreSolicitante,
            String dniSolicitante,
            Long idApartamento,
            Long idPropietario,
            Long idInquilino,
            ConfiguracionModel configuracion,
            int prestamosActuales) {
        if (!configuracion.puedeUsarCarrito(prestamosActuales))
            throw LogPrestamoCarritoException.limiteAlcanzado();
        carrito.actualizarEstado(EstadoCarrito.EN_USO);
        return new LogPrestamoCarritoModel(
            nombreSolicitante, dniSolicitante, idApartamento,
            carrito.getId(), idPropietario, idInquilino);
    }

    public void devolver(
            CarritoModel carrito,
            LogPrestamoCarritoModel log,
            ConfiguracionModel configuracion) {
        carrito.actualizarEstado(EstadoCarrito.DISPONIBLE);
        var minutos = Duration.between(log.getFechaPrestamo(), Instant.now()).toMinutes();
        BigDecimal penalizacion = BigDecimal.ZERO;
        if (configuracion.tiempoExcedeLimitePrestamo((int) minutos)) {
            var excedidos = (int) (minutos - configuracion.getMaxTiempoPrestamoMin());
            penalizacion = configuracion.calcularPenalizacion(excedidos);
        }
        log.registrarDevolucion(penalizacion);
    }
}
