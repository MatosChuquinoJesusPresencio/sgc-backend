package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "estacionamientos", indexes = {
    @Index(name = "idx_estacionamientos_id_condominio", columnList = "id_condominio"),
    @Index(name = "idx_estacionamientos_id_apartamento", columnList = "id_apartamento")
})
@Getter @Setter @NoArgsConstructor
public class EstacionamientoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo", columnDefinition = "TEXT")
    private TipoVehiculo tipoVehiculo;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "cantidad_actual", nullable = false)
    private Integer cantidadActual;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @Column(name = "id_apartamento")
    private Long idApartamento;

    @Column(name = "id_condominio", nullable = false)
    private Long idCondominio;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
