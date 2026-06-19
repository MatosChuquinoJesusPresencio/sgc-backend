package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "vehiculos", indexes = {
    @Index(name = "idx_vehiculos_placa", columnList = "placa", unique = true),
    @Index(name = "idx_vehiculos_id_condominio", columnList = "id_condominio"),
    @Index(name = "idx_vehiculos_id_propietario", columnList = "id_propietario"),
    @Index(name = "idx_vehiculos_id_inquilino", columnList = "id_inquilino"),
    @Index(name = "idx_vehiculos_id_estacionamiento", columnList = "id_estacionamiento")
})
@Getter @Setter @NoArgsConstructor
public class VehiculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa", columnDefinition = "TEXT", nullable = false)
    private String placa;

    @Column(name = "marca", columnDefinition = "TEXT", nullable = false)
    private String marca;

    @Column(name = "modelo", columnDefinition = "TEXT", nullable = false)
    private String modelo;

    @Column(name = "color", columnDefinition = "TEXT", nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", columnDefinition = "TEXT", nullable = false)
    private TipoVehiculo tipo;

    @Column(name = "id_condominio", nullable = false)
    private Long idCondominio;

    @Column(name = "id_propietario")
    private Long idPropietario;

    @Column(name = "id_inquilino")
    private Long idInquilino;

    @Column(name = "id_estacionamiento")
    private Long idEstacionamiento;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
