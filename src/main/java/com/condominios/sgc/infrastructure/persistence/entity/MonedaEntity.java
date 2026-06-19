package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "monedas")
@Getter @Setter @NoArgsConstructor
public class MonedaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "TEXT", nullable = false)
    private String nombre;

    @Column(name = "codigo", columnDefinition = "TEXT", nullable = false)
    private String codigo;

    @Column(name = "simbolo", columnDefinition = "TEXT", nullable = false)
    private String simbolo;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
