package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paises")
@NoArgsConstructor
@AllArgsConstructor
public class PaisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "codigo_iso", nullable = false, unique = true)
    private String codigoIso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moneda")
    private MonedaEntity moneda;

    @Column(name = "id_moneda", insertable = false, updatable = false)
    private Long idMoneda;

    @OneToMany(mappedBy = "pais")
    private List<CondominioEntity> condominios = new ArrayList<>();

    @OneToMany(mappedBy = "pais")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CiudadEntity> ciudades = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigoIso() { return codigoIso; }
    public void setCodigoIso(String codigoIso) { this.codigoIso = codigoIso; }
    public MonedaEntity getMoneda() { return moneda; }
    public void setMoneda(MonedaEntity moneda) { this.moneda = moneda; }
    public Long getIdMoneda() { return idMoneda; }
    public void setIdMoneda(Long idMoneda) { this.idMoneda = idMoneda; }
    public List<CiudadEntity> getCiudades() { return ciudades; }
    public void setCiudades(List<CiudadEntity> ciudades) { this.ciudades = ciudades; }
}
