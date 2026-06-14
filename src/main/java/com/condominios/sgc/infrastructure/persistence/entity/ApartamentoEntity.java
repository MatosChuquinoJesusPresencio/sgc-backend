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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartamentos")
@NoArgsConstructor
@AllArgsConstructor
public class ApartamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(name = "derecho_estacionamiento")
    private Boolean derechoEstacionamiento;

    private BigDecimal metraje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario")
    private UsuarioEntity propietario;

    @Column(name = "id_propietario", insertable = false, updatable = false)
    private Long idPropietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso")
    private PisoEntity piso;

    @Column(name = "id_piso", insertable = false, updatable = false)
    private Long idPiso;

    @OneToMany(mappedBy = "apartamento")
    private List<InquilinoEntity> inquilinos = new ArrayList<>();

    @OneToMany(mappedBy = "apartamento")
    private List<EstacionamientoEntity> estacionamientos = new ArrayList<>();

    @OneToMany(mappedBy = "apartamento")
    private List<LogPrestamoCarritoEntity> logsPrestamoCarrito = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Boolean getDerechoEstacionamiento() { return derechoEstacionamiento; }
    public void setDerechoEstacionamiento(Boolean derechoEstacionamiento) { this.derechoEstacionamiento = derechoEstacionamiento; }
    public BigDecimal getMetraje() { return metraje; }
    public void setMetraje(BigDecimal metraje) { this.metraje = metraje; }
    public UsuarioEntity getPropietario() { return propietario; }
    public void setPropietario(UsuarioEntity propietario) { this.propietario = propietario; }
    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
    public PisoEntity getPiso() { return piso; }
    public void setPiso(PisoEntity piso) { this.piso = piso; }
    public Long getIdPiso() { return idPiso; }
    public void setIdPiso(Long idPiso) { this.idPiso = idPiso; }
    public List<InquilinoEntity> getInquilinos() { return inquilinos; }
    public List<EstacionamientoEntity> getEstacionamientos() { return estacionamientos; }
    public List<LogPrestamoCarritoEntity> getLogsPrestamoCarrito() { return logsPrestamoCarrito; }
}
