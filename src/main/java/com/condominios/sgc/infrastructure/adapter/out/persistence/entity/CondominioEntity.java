package com.condominios.sgc.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condominio")
@Getter
@Setter
@NoArgsConstructor
public class CondominioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(name = "pais_id")
    private Long idPais;

    @Column(name = "ciudad_id")
    private Long idCiudad;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "condominio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TorreEntity> torres = new ArrayList<>();

    @OneToOne(mappedBy = "condominio", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConfiguracionEntity configuracion;
}
