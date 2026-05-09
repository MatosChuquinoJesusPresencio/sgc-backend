package com.condominios.sgc.domain.model;

import java.math.BigDecimal;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.exception.ConfiguracionException;

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

    public ConfiguracionModel(
            Long id, Integer maxAutos, Integer maxMotos, BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin, Integer maxEstacionamientosPorApartamento, Integer maxCarritosPorApartamento,
            Integer maxVehiculosPorPropietario, Integer maxInquilinosPorApartamento) {
        this.id = id;
        validarYAsignarDatos(maxAutos, maxMotos, penalizacionPorMin, maxTiempoPrestamoMin,
                maxEstacionamientosPorApartamento, maxCarritosPorApartamento, maxVehiculosPorPropietario,
                maxInquilinosPorApartamento);
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

    private void validarYAsignarDatos(Integer maxAutos, Integer maxMotos, BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin, Integer maxEstacionamientosPorApartamento,
            Integer maxCarritosPorApartamento, Integer maxVehiculosPorPropietario, Integer maxInquilinosPorApartamento) {

        if (maxAutos == null || maxAutos < 0) {
            throw ConfiguracionException.maxAutosInvalido();
        }
        this.maxAutos = maxAutos;

        if (maxMotos == null || maxMotos < 0) {
            throw ConfiguracionException.maxMotosInvalido();
        }
        this.maxMotos = maxMotos;

        if (penalizacionPorMin == null || penalizacionPorMin.compareTo(BigDecimal.ZERO) < 0) {
            throw ConfiguracionException.penalizacionInvalida();
        }
        this.penalizacionPorMin = penalizacionPorMin;

        if (maxTiempoPrestamoMin == null || maxTiempoPrestamoMin < 0) {
            throw ConfiguracionException.tiempoPrestamoInvalido();
        }
        this.maxTiempoPrestamoMin = maxTiempoPrestamoMin;

        if (maxEstacionamientosPorApartamento == null || maxEstacionamientosPorApartamento < 0) {
            throw ConfiguracionException.maxEstacionamientosInvalido();
        }
        this.maxEstacionamientosPorApartamento = maxEstacionamientosPorApartamento;

        if (maxCarritosPorApartamento == null || maxCarritosPorApartamento < 0) {
            throw ConfiguracionException.maxCarritosInvalido();
        }
        this.maxCarritosPorApartamento = maxCarritosPorApartamento;

        if (maxVehiculosPorPropietario == null || maxVehiculosPorPropietario < 0) {
            throw ConfiguracionException.maxVehiculosPorPropietarioInvalido();
        }
        this.maxVehiculosPorPropietario = maxVehiculosPorPropietario;

        if (maxInquilinosPorApartamento == null || maxInquilinosPorApartamento < 0) {
            throw ConfiguracionException.maxInquilinosInvalido();
        }
        this.maxInquilinosPorApartamento = maxInquilinosPorApartamento;
    }

    public void actualizarDatos(Integer maxAutos, Integer maxMotos, BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin, Integer maxEstacionamientosPorApartamento, Integer maxCarritosPorApartamento,
            Integer maxVehiculosPorPropietario, Integer maxInquilinosPorApartamento) {

        validarYAsignarDatos(maxAutos, maxMotos, penalizacionPorMin, maxTiempoPrestamoMin,
                maxEstacionamientosPorApartamento, maxCarritosPorApartamento, maxVehiculosPorPropietario,
                maxInquilinosPorApartamento);
    }

    public void puedoEstacionarVehiculo(TipoVehiculo tipoVehiculo, Integer vehiculosPorTipo){
        if(tipoVehiculo == TipoVehiculo.AUTO){
            if(vehiculosPorTipo >= maxAutos)
                throw ConfiguracionException.vehiculosPorTipoExcedidos(tipoVehiculo.toString(), maxAutos, vehiculosPorTipo);
        }else if(tipoVehiculo == TipoVehiculo.MOTO){
            if(vehiculosPorTipo >= maxMotos)
                throw ConfiguracionException.vehiculosPorTipoExcedidos(tipoVehiculo.toString(), maxMotos, vehiculosPorTipo);
        }else{
            throw ConfiguracionException.tipoVehiculoNoSoportado(tipoVehiculo.toString());
        }
    }
    
    public void verificarTiempoPrestamoExcedido(Integer tiempoPrestamo) {
        if (tiempoPrestamo > maxTiempoPrestamoMin)
            throw ConfiguracionException.tiempoPrestamoExcedido(maxTiempoPrestamoMin, tiempoPrestamo);
    }

    public void puedeAgregarVehiculoAlPropietario(Integer vehiculosDelPropietario) {
        if (vehiculosDelPropietario >= maxVehiculosPorPropietario)
            throw ConfiguracionException.vehiculosPorPropietarioExcedidos(maxVehiculosPorPropietario,
                    vehiculosDelPropietario);
    }

    public void puedoUsarEstacionamiento(Integer estacionamientosEnUsoPorApartamento) {
        if (estacionamientosEnUsoPorApartamento >= maxEstacionamientosPorApartamento)
            throw ConfiguracionException.estacionamientosExcedidosPorApartamento(maxEstacionamientosPorApartamento,
                    estacionamientosEnUsoPorApartamento);
    }

    public void puedoSolicitarCarrito(Integer carritosEnUsoPorApartamento) {
        if (carritosEnUsoPorApartamento >= maxCarritosPorApartamento)
            throw ConfiguracionException.carritosExcedidosPorApartamento(maxCarritosPorApartamento,
                    carritosEnUsoPorApartamento);
    }

    public void puedoAgregarInquilino(Integer inquilinosPorApartamento) {
        if (inquilinosPorApartamento >= maxInquilinosPorApartamento)
            throw ConfiguracionException.inquilinosPorApartamentoExcedidos(maxInquilinosPorApartamento,
                    inquilinosPorApartamento);
    }

    public static ConfiguracionModel obtenerConfiguracionBase() {
        return new ConfiguracionModel(null,
                    2,
                    1,
                    new BigDecimal("0.50"),
                    1440,
                    2,
                    1,
                    2,
                    2);
    }
}
