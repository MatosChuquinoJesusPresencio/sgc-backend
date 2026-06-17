package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condominios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CondominioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais")
    private PaisEntity pais;

    @Column(name = "id_pais", insertable = false, updatable = false)
    private Long idPais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudad")
    private CiudadEntity ciudad;

    @Column(name = "id_ciudad", insertable = false, updatable = false)
    private Long idCiudad;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @OneToMany(mappedBy = "condominio")
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<TorreEntity> torres = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<CarritoEntity> carritos = new ArrayList<>();

    @OneToOne(mappedBy = "condominio")
    private ConfiguracionEntity configuracion;

    @OneToMany(mappedBy = "condominio")
    private List<EstacionamientoEntity> estacionamientos = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<VehiculoEntity> vehiculos = new ArrayList<>();
}
