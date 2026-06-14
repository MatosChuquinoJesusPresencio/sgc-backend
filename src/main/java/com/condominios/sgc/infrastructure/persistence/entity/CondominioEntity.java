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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condominios")
@NoArgsConstructor
@AllArgsConstructor
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

    private String direccion;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @OneToMany(mappedBy = "condominio")
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<TorreEntity> torres = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<CarritoEntity> carritos = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<ConfiguracionEntity> configuraciones = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<EstacionamientoEntity> estacionamientos = new ArrayList<>();

    @OneToMany(mappedBy = "condominio")
    private List<VehiculoEntity> vehiculos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getIdPais() { return idPais; }
    public void setIdPais(Long idPais) { this.idPais = idPais; }
    public Long getIdCiudad() { return idCiudad; }
    public void setIdCiudad(Long idCiudad) { this.idCiudad = idCiudad; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Instant fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public List<UsuarioEntity> getUsuarios() { return usuarios; }
    public void setUsuarios(List<UsuarioEntity> usuarios) { this.usuarios = usuarios; }
    public List<TorreEntity> getTorres() { return torres; }
    public void setTorres(List<TorreEntity> torres) { this.torres = torres; }
    public List<CarritoEntity> getCarritos() { return carritos; }
    public void setCarritos(List<CarritoEntity> carritos) { this.carritos = carritos; }
    public List<ConfiguracionEntity> getConfiguraciones() { return configuraciones; }
    public void setConfiguraciones(List<ConfiguracionEntity> configuraciones) { this.configuraciones = configuraciones; }
    public List<EstacionamientoEntity> getEstacionamientos() { return estacionamientos; }
    public void setEstacionamientos(List<EstacionamientoEntity> estacionamientos) { this.estacionamientos = estacionamientos; }
    public List<VehiculoEntity> getVehiculos() { return vehiculos; }
    public void setVehiculos(List<VehiculoEntity> vehiculos) { this.vehiculos = vehiculos; }
    public PaisEntity getPais() { return pais; }
    public void setPais(PaisEntity pais) { this.pais = pais; }
    public CiudadEntity getCiudad() { return ciudad; }
    public void setCiudad(CiudadEntity ciudad) { this.ciudad = ciudad; }
}
