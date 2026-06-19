package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.TipoToken;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tokens", indexes = {
    @Index(name = "idx_tokens_token", columnList = "token", unique = true),
    @Index(name = "idx_tokens_id_usuario", columnList = "id_usuario")
})
@Getter @Setter @NoArgsConstructor
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", columnDefinition = "TEXT", nullable = false)
    private TipoToken tipo;

    @Column(name = "token", columnDefinition = "TEXT", nullable = false)
    private String token;

    @Column(name = "expiracion", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant expiracion;

    @Column(name = "usado", nullable = false)
    private Boolean usado;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
