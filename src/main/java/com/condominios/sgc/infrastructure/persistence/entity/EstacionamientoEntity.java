package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "estacionamientos")
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "cantidad_actual")
    private Integer cantidadActual;

    private Boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private ApartamentoEntity apartamento;

    @Column(name = "id_apartamento", insertable = false, updatable = false)
    private Long idApartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private CondominioEntity condominio;

    @Column(name = "id_condominio", insertable = false, updatable = false)
    private Long idCondominio;

    @OneToMany(mappedBy = "estacionamiento")
    private List<VehiculoEntity> vehiculos = new ArrayList<>();

    @OneToMany(mappedBy = "estacionamiento")
    private List<LogAccesoVehicularEntity> logsAccesoVehicular = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    public Integer getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(Integer cantidadActual) { this.cantidadActual = cantidadActual; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    public ApartamentoEntity getApartamento() { return apartamento; }
    public void setApartamento(ApartamentoEntity apartamento) { this.apartamento = apartamento; }
    public Long getIdApartamento() { return idApartamento; }
    public void setIdApartamento(Long idApartamento) { this.idApartamento = idApartamento; }
    public CondominioEntity getCondominio() { return condominio; }
    public void setCondominio(CondominioEntity condominio) { this.condominio = condominio; }
    public Long getIdCondominio() { return idCondominio; }
    public void setIdCondominio(Long idCondominio) { this.idCondominio = idCondominio; }
}
