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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ciudades")
@NoArgsConstructor
@AllArgsConstructor
public class CiudadEntity {

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

    @OneToMany(mappedBy = "ciudad")
    private List<CondominioEntity> condominios = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public PaisEntity getPais() { return pais; }
    public void setPais(PaisEntity pais) { this.pais = pais; }
    public Long getIdPais() { return idPais; }
    public void setIdPais(Long idPais) { this.idPais = idPais; }
}
