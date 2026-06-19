package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "apartamentos", indexes = {
    @Index(name = "idx_apartamentos_id_piso", columnList = "id_piso")
})
@Getter @Setter @NoArgsConstructor
public class ApartamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "derecho_estacionamiento", nullable = false)
    private Boolean derechoEstacionamiento;

    @Column(name = "metraje", columnDefinition = "NUMERIC(10,2)", nullable = false)
    private BigDecimal metraje;

    @Column(name = "id_propietario")
    private Long idPropietario;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso", nullable = false)
    private PisoEntity piso;
}
