package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

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
    private Long condominioId;

    public ConfiguracionModel(
            Long id,
            Integer maxAutos,
            Integer maxMotos,
            BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin,
            Integer maxEstacionamientosPorApartamento,
            Integer maxCarritosPorApartamento,
            Integer maxVehiculosPorPropietario,
            Integer maxInquilinosPorApartamento,
            Long condominioId
        ) {
        this(maxAutos,
                maxMotos,
                penalizacionPorMin,
                maxTiempoPrestamoMin,
                maxEstacionamientosPorApartamento,
                maxCarritosPorApartamento,
                maxVehiculosPorPropietario,
                maxInquilinosPorApartamento,
                condominioId);
        this.id = id;
    }

        public ConfiguracionModel(
            Integer maxAutos,
            Integer maxMotos,
            BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin,
            Integer maxEstacionamientosPorApartamento,
            Integer maxCarritosPorApartamento,
            Integer maxVehiculosPorPropietario,
            Integer maxInquilinosPorApartamento,
            Long condominioId
        ) {
        validarYAsignarDatos(maxAutos, maxMotos, penalizacionPorMin, maxTiempoPrestamoMin,
                maxEstacionamientosPorApartamento, maxCarritosPorApartamento, maxVehiculosPorPropietario,
                maxInquilinosPorApartamento, condominioId);
    }

    private void validarYAsignarDatos(Integer maxAutos, Integer maxMotos, BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin, Integer maxEstacionamientosPorApartamento,
            Integer maxCarritosPorApartamento, Integer maxVehiculosPorPropietario, Integer maxInquilinosPorApartamento,
            Long condominioId) {
        this.maxAutos = requerirNoNegativo(maxAutos, ConfiguracionException::maximoAutosInvalido);
        this.maxMotos = requerirNoNegativo(maxMotos, ConfiguracionException::maximoMotosInvalido);
        this.penalizacionPorMin = requerirNoNegativo(penalizacionPorMin, ConfiguracionException::penalizacionInvalida);
        this.maxTiempoPrestamoMin = requerirNoNegativo(maxTiempoPrestamoMin, ConfiguracionException::maximoTiempoPrestamoInvalido);
        this.maxEstacionamientosPorApartamento = requerirNoNegativo(maxEstacionamientosPorApartamento, ConfiguracionException::maximoEstacionamientosInvalido);
        this.maxCarritosPorApartamento = requerirNoNegativo(maxCarritosPorApartamento, ConfiguracionException::maximoCarritosInvalido);
        this.maxVehiculosPorPropietario = requerirNoNegativo(maxVehiculosPorPropietario, ConfiguracionException::maximoVehiculosPorPropietarioInvalido);
        this.maxInquilinosPorApartamento = requerirNoNegativo(maxInquilinosPorApartamento, ConfiguracionException::maximoInquilinosInvalido);
        this.condominioId = requerirNoNulo(condominioId, ConfiguracionException::condominioIdObligatorio);
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
    public Long getCondominioId() { return condominioId; }

    public void actualizarDatos(Integer maxAutos, Integer maxMotos, BigDecimal penalizacionPorMin,
            Integer maxTiempoPrestamoMin, Integer maxEstacionamientosPorApartamento, Integer maxCarritosPorApartamento,
            Integer maxVehiculosPorPropietario, Integer maxInquilinosPorApartamento) {

        validarYAsignarDatos(maxAutos, maxMotos, penalizacionPorMin, maxTiempoPrestamoMin,
                maxEstacionamientosPorApartamento, maxCarritosPorApartamento, maxVehiculosPorPropietario,
                maxInquilinosPorApartamento, this.condominioId);
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
}
