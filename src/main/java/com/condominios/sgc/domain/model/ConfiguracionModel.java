package com.condominios.sgc.domain.model;

import java.math.BigDecimal;

import com.condominios.sgc.domain.type.TipoVehiculo;
import com.condominios.sgc.domain.shared.exception.ConfiguracionException;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class ConfiguracionModel {
    private Long id;
    private Integer maxAutos;
    private Integer maxMotos;
    private BigDecimal penalizacionPorMin;
    private Integer maxTiempoPrestamoMin;
    private Integer maxEstacionamientosPorApartamento;
    private Integer maxCarritosPorApartamento;
    private Integer maxVehiculosPorPropietario;
    private Integer maxInquilinosPorApartamento;

    public ConfiguracionModel(Long id, Integer maxAutos, Integer maxMotos, 
        BigDecimal penalizacionPorMin, Integer maxTiempoPrestamoMin, 
        Integer maxEstacionamientosPorApartamento, Integer maxCarritosPorApartamento,
        Integer maxVehiculosPorPropietario, Integer maxInquilinosPorApartamento) {
        this.id = id;
        this.maxAutos = maxAutos;
        this.maxMotos = maxMotos;
        this.penalizacionPorMin = penalizacionPorMin;
        this.maxTiempoPrestamoMin = maxTiempoPrestamoMin;
        this.maxEstacionamientosPorApartamento = maxEstacionamientosPorApartamento;
        this.maxCarritosPorApartamento = maxCarritosPorApartamento;
        this.maxVehiculosPorPropietario = maxVehiculosPorPropietario;
        this.maxInquilinosPorApartamento = maxInquilinosPorApartamento;
    }

    public Long getId() { return id; }
    public Integer getMaxAutos() { return maxAutos; }
    public Integer getMaxMotos() { return maxMotos; }
    public BigDecimal getPenalizacionPorMin() { return penalizacionPorMin; }
    public Integer getMaxTiempoPrestamoMin() { return maxTiempoPrestamoMin; }
    public Integer getMaxEstacionamientosPorApartamento() { return maxEstacionamientosPorApartamento; }
    public Integer getMaxCarritosPorApartamento() { return maxCarritosPorApartamento; }
    public Integer getMaxVehiculosPorPropietario() { return maxVehiculosPorPropietario; }
    public Integer getMaxInquilinosPorApartamento() { return maxInquilinosPorApartamento; }
    
    public void actualizar(Integer maxAutos, Integer maxMotos, BigDecimal penalizacionPorMin, 
            Integer maxTiempoPrestamoMin, Integer maxEstacionamientosPorApartamento, 
            Integer maxCarritosPorApartamento, Integer maxVehiculosPorPropietario, 
            Integer maxInquilinosPorApartamento) {
        this.maxAutos = noNulo(maxAutos, ConfiguracionException::maxAutosRequerido);
        this.maxMotos = noNulo(maxMotos, ConfiguracionException::maxMotosRequerido);
        this.penalizacionPorMin = noNulo(penalizacionPorMin, ConfiguracionException::penalizacionRequerida);
        this.maxTiempoPrestamoMin = noNulo(maxTiempoPrestamoMin, ConfiguracionException::maxTiempoRequerido);
        this.maxEstacionamientosPorApartamento = noNulo(maxEstacionamientosPorApartamento, ConfiguracionException::maxEstacionamientosRequerido);
        this.maxCarritosPorApartamento = noNulo(maxCarritosPorApartamento, ConfiguracionException::maxCarritosRequerido);
        this.maxVehiculosPorPropietario = noNulo(maxVehiculosPorPropietario, ConfiguracionException::maxVehiculosRequerido);
        this.maxInquilinosPorApartamento = noNulo(maxInquilinosPorApartamento, ConfiguracionException::maxInquilinosRequerido);
    }

    public boolean puedeAgregarVehiculo(TipoVehiculo tipo, int cantidadActual) {
        int max = tipo == TipoVehiculo.AUTO ? maxAutos : maxMotos;
        return cantidadActual < max;
    }

    public boolean puedeAsignarEstacionamiento(int cantidadActual) {
        return cantidadActual < maxEstacionamientosPorApartamento;
    }

    public boolean puedeUsarCarrito(int cantidadActual) {
        return cantidadActual < maxCarritosPorApartamento;
    }

    public boolean puedeAgregarVehiculoPropietario(int cantidadActual) {
        return cantidadActual < maxVehiculosPorPropietario;
    }

    public boolean puedeAgregarInquilino(int cantidadActual) {
        return cantidadActual < maxInquilinosPorApartamento;
    }

    public boolean tiempoExcedeLimitePrestamo(int minutos) {
        return minutos > maxTiempoPrestamoMin;
    }

    public BigDecimal calcularPenalizacion(int minutosExcedidos) {
        return penalizacionPorMin.multiply(BigDecimal.valueOf(minutosExcedidos));
    }

    public static ConfiguracionModel nuevo() {
        return new ConfiguracionModel(
            null, 
            2, 
            4, 
            new BigDecimal(1), 
            30, 
            2, 
            2, 
            2, 
            2);
    }
}
