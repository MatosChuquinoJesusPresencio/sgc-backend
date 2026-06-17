package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "configuraciones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", unique = true)
    private CondominioEntity condominio;

    @Column(name = "id_condominio", insertable = false, updatable = false, unique = true)
    private Long idCondominio;
}
