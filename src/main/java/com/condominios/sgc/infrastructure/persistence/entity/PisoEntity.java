package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pisos", indexes = {
    @Index(name = "idx_pisos_id_torre", columnList = "id_torre")
})
@Getter @Setter @NoArgsConstructor
public class PisoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_torre", nullable = false)
    private TorreEntity torre;

    @OneToMany(mappedBy = "piso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApartamentoEntity> apartamentos = new ArrayList<>();
}
