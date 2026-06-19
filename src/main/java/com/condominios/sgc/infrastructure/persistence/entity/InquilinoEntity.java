package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.TipoDocumento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "inquilinos", indexes = {
    @Index(name = "idx_inquilinos_id_apartamento", columnList = "id_apartamento")
})
@Getter @Setter @NoArgsConstructor
public class InquilinoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres", columnDefinition = "TEXT", nullable = false)
    private String nombres;

    @Column(name = "apellidos", columnDefinition = "TEXT", nullable = false)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", columnDefinition = "TEXT", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", columnDefinition = "TEXT", nullable = false)
    private String numeroDocumento;

    @Column(name = "id_apartamento", nullable = false)
    private Long idApartamento;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
