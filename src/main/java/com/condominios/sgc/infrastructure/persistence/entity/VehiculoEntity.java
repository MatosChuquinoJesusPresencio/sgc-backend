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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private List<LogAccesoVehicularEntity> logsAccesoVehicular = new ArrayList<>();
}
