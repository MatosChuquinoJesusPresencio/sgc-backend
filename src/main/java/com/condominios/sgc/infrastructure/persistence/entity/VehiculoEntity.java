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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculos")
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario")
    private UsuarioEntity propietario;

    @Column(name = "id_propietario", insertable = false, updatable = false)
    private Long idPropietario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inquilino", unique = true)
    private InquilinoEntity inquilino;

    @Column(name = "id_inquilino", insertable = false, updatable = false)
    private Long idInquilino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacionamiento")
    private EstacionamientoEntity estacionamiento;

    @Column(name = "id_estacionamiento", insertable = false, updatable = false)
    private Long idEstacionamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private CondominioEntity condominio;

    @Column(name = "id_condominio", insertable = false, updatable = false)
    private Long idCondominio;

    @OneToMany(mappedBy = "vehiculo")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<LogAccesoVehicularEntity> logsAccesoVehicular = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public TipoVehiculo getTipo() { return tipo; }
    public void setTipo(TipoVehiculo tipo) { this.tipo = tipo; }
    public UsuarioEntity getPropietario() { return propietario; }
    public void setPropietario(UsuarioEntity propietario) { this.propietario = propietario; }
    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
    public InquilinoEntity getInquilino() { return inquilino; }
    public void setInquilino(InquilinoEntity inquilino) { this.inquilino = inquilino; }
    public Long getIdInquilino() { return idInquilino; }
    public void setIdInquilino(Long idInquilino) { this.idInquilino = idInquilino; }
    public EstacionamientoEntity getEstacionamiento() { return estacionamiento; }
    public void setEstacionamiento(EstacionamientoEntity estacionamiento) { this.estacionamiento = estacionamiento; }
    public Long getIdEstacionamiento() { return idEstacionamiento; }
    public void setIdEstacionamiento(Long idEstacionamiento) { this.idEstacionamiento = idEstacionamiento; }
    public CondominioEntity getCondominio() { return condominio; }
    public void setCondominio(CondominioEntity condominio) { this.condominio = condominio; }
    public Long getIdCondominio() { return idCondominio; }
    public void setIdCondominio(Long idCondominio) { this.idCondominio = idCondominio; }
}
