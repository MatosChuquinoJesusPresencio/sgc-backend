package com.condominios.sgc.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "configuracion")
@Getter
@Setter
@NoArgsConstructor
public class ConfiguracionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer maxAutos;

    @Column(nullable = false)
    private Integer maxMotos;

    @Column(nullable = false)
    private BigDecimal penalizacionPorMin;

    @Column(nullable = false)
    private Integer maxTiempoPrestamoMin;

    @Column(nullable = false)
    private Integer maxEstacionamientosPorApartamento;

    @Column(nullable = false)
    private Integer maxCarritosPorApartamento;

    @Column(nullable = false)
    private Integer maxVehiculosPorPropietario;

    @Column(nullable = false)
    private Integer maxInquilinosPorApartamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id", nullable = false, unique = true)
    private CondominioEntity condominio;
}
