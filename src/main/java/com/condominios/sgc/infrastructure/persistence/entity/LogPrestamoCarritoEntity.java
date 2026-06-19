package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.TipoHabitante;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "logs_prestamo_carrito", indexes = {
    @Index(name = "idx_logs_prestamo_id_apartamento", columnList = "id_apartamento"),
    @Index(name = "idx_logs_prestamo_id_carrito", columnList = "id_carrito")
})
@Getter @Setter @NoArgsConstructor
public class LogPrestamoCarritoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "solicitante", columnDefinition = "TEXT", nullable = false)
    private TipoHabitante solicitante;

    @Column(name = "nombre_solicitante", columnDefinition = "TEXT", nullable = false)
    private String nombreSolicitante;

    @Column(name = "dni_solicitante", columnDefinition = "TEXT", nullable = false)
    private String dniSolicitante;

    @Column(name = "penalizacion", columnDefinition = "NUMERIC(10,2)")
    private BigDecimal penalizacion;

    @Column(name = "fecha_prestamo", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant fechaPrestamo;

    @Column(name = "fecha_devolucion", columnDefinition = "TIMESTAMPTZ")
    private Instant fechaDevolucion;

    @Column(name = "id_apartamento", nullable = false)
    private Long idApartamento;

    @Column(name = "id_carrito", nullable = false)
    private Long idCarrito;

    @Column(name = "id_inquilino")
    private Long idInquilino;

    @Column(name = "id_propietario")
    private Long idPropietario;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
