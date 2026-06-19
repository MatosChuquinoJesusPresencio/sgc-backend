package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.type.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "usuarios", indexes = {
    @Index(name = "idx_usuarios_correo", columnList = "correo", unique = true),
    @Index(name = "idx_usuarios_id_condominio", columnList = "id_condominio")
})
@Getter @Setter @NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres", columnDefinition = "TEXT", nullable = false)
    private String nombres;

    @Column(name = "apellidos", columnDefinition = "TEXT", nullable = false)
    private String apellidos;

    @Column(name = "correo", columnDefinition = "TEXT", nullable = false)
    private String correo;

    @Column(name = "telefono", columnDefinition = "TEXT")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", columnDefinition = "TEXT", nullable = false)
    private Rol rol;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "contrasena", columnDefinition = "TEXT", nullable = false)
    private String contrasena;

    @Column(name = "correo_pendiente", columnDefinition = "TEXT")
    private String correoPendiente;

    @Column(name = "correo_verificado", nullable = false)
    private Boolean correoVerificado;

    @Column(name = "id_condominio")
    private Long idCondominio;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant fechaCreacion;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;
}
