package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "configuraciones")
@Getter @Setter @NoArgsConstructor
public class ConfiguracionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_autos")
    private Integer maxAutos;

    @Column(name = "max_motos")
    private Integer maxMotos;

    @Column(name = "penalizacion_por_min", columnDefinition = "NUMERIC(10,2)")
    private java.math.BigDecimal penalizacionPorMin;

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

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio", nullable = false)
    private CondominioEntity condominio;
}
