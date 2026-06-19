package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condominios")
@Getter @Setter @NoArgsConstructor
public class CondominioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "TEXT", nullable = false)
    private String nombre;

    @Column(name = "id_pais", nullable = false)
    private Long idPais;

    @Column(name = "id_ciudad", nullable = false)
    private Long idCiudad;

    @Column(name = "direccion", columnDefinition = "TEXT", nullable = false)
    private String direccion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant fechaCreacion;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "condominio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TorreEntity> torres = new ArrayList<>();

    @OneToOne(mappedBy = "condominio", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConfiguracionEntity configuracion;
}
