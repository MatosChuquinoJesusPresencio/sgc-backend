package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "configuraciones")
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_autos")
    private Integer maxAutos;

    @Column(name = "max_motos")
    private Integer maxMotos;

    @Column(name = "penalizacion_por_min", precision = 10, scale = 2)
    private BigDecimal penalizacionPorMin;

    @Column(name = "max_tiempo_prestamo_min")
    private Integer maxTiempoPrestamoMin;

    @Column(name = "max_estacionamientos_por_apartamento")
    private Integer maxEstacionamientosPorApartamento;

    @Column(name = "max_carritos_por_apartamento")
    private Integer maxCarritosPorApartamento;

    @Column(name = "max_vehiculos_por_propietario")
    private Integer maxVehiculosPorPropietario;

    @Column(name = "max_inquilinos_por_apartamento")
    private Integer maxInquilinosPorApartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", unique = true)
    private CondominioEntity condominio;

    @Column(name = "id_condominio", insertable = false, updatable = false, unique = true)
    private Long idCondominio;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getMaxAutos() { return maxAutos; }
    public void setMaxAutos(Integer maxAutos) { this.maxAutos = maxAutos; }
    public Integer getMaxMotos() { return maxMotos; }
    public void setMaxMotos(Integer maxMotos) { this.maxMotos = maxMotos; }
    public BigDecimal getPenalizacionPorMin() { return penalizacionPorMin; }
    public void setPenalizacionPorMin(BigDecimal penalizacionPorMin) { this.penalizacionPorMin = penalizacionPorMin; }
    public Integer getMaxTiempoPrestamoMin() { return maxTiempoPrestamoMin; }
    public void setMaxTiempoPrestamoMin(Integer maxTiempoPrestamoMin) { this.maxTiempoPrestamoMin = maxTiempoPrestamoMin; }
    public Integer getMaxEstacionamientosPorApartamento() { return maxEstacionamientosPorApartamento; }
    public void setMaxEstacionamientosPorApartamento(Integer maxEstacionamientosPorApartamento) { this.maxEstacionamientosPorApartamento = maxEstacionamientosPorApartamento; }
    public Integer getMaxCarritosPorApartamento() { return maxCarritosPorApartamento; }
    public void setMaxCarritosPorApartamento(Integer maxCarritosPorApartamento) { this.maxCarritosPorApartamento = maxCarritosPorApartamento; }
    public Integer getMaxVehiculosPorPropietario() { return maxVehiculosPorPropietario; }
    public void setMaxVehiculosPorPropietario(Integer maxVehiculosPorPropietario) { this.maxVehiculosPorPropietario = maxVehiculosPorPropietario; }
    public Integer getMaxInquilinosPorApartamento() { return maxInquilinosPorApartamento; }
    public void setMaxInquilinosPorApartamento(Integer maxInquilinosPorApartamento) { this.maxInquilinosPorApartamento = maxInquilinosPorApartamento; }
    public CondominioEntity getCondominio() { return condominio; }
    public void setCondominio(CondominioEntity condominio) { this.condominio = condominio; }
    public Long getIdCondominio() { return idCondominio; }
    public void setIdCondominio(Long idCondominio) { this.idCondominio = idCondominio; }
}
