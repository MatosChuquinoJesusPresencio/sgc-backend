package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.MetodoEntrada;
import com.condominios.sgc.domain.type.TipoHabitante;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "logs_acceso_vehicular", indexes = {
    @Index(name = "idx_logs_acceso_placa", columnList = "placa"),
    @Index(name = "idx_logs_acceso_id_vehiculo", columnList = "id_vehiculo"),
    @Index(name = "idx_logs_acceso_id_estacionamiento", columnList = "id_estacionamiento")
})
@Getter @Setter @NoArgsConstructor
public class LogAccesoVehicularEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa", columnDefinition = "TEXT", nullable = false)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(name = "ocupante", columnDefinition = "TEXT", nullable = false)
    private TipoHabitante ocupante;

    @Column(name = "datos_inquilino", columnDefinition = "TEXT")
    private String datosInquilino;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo", columnDefinition = "TEXT", nullable = false)
    private MetodoEntrada metodo;

    @Column(name = "fecha_entrada", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant fechaEntrada;

    @Column(name = "fecha_salida", columnDefinition = "TIMESTAMPTZ")
    private Instant fechaSalida;

    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @Column(name = "id_estacionamiento")
    private Long idEstacionamiento;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
