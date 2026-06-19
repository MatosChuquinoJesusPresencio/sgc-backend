package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.EstadoCarrito;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "carritos", indexes = {
    @Index(name = "idx_carritos_id_condominio", columnList = "id_condominio"),
    @Index(name = "idx_carritos_codigo", columnList = "codigo", unique = true)
})
@Getter @Setter @NoArgsConstructor
public class CarritoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", columnDefinition = "TEXT", nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", columnDefinition = "TEXT", nullable = false)
    private EstadoCarrito estado;

    @Column(name = "id_condominio", nullable = false)
    private Long idCondominio;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
