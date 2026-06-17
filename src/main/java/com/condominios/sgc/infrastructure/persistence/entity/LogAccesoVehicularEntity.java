package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "logs_acceso_vehicular")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogAccesoVehicularEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitante ocupante;

    @Column(name = "datos_inquilino")
    private String datosInquilino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoEntrada metodo;

    @Column(name = "fecha_entrada", nullable = false)
    private Instant fechaEntrada;

    @Column(name = "fecha_salida")
    private Instant fechaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private VehiculoEntity vehiculo;

    @Column(name = "id_vehiculo", insertable = false, updatable = false)
    private Long idVehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacionamiento")
    private EstacionamientoEntity estacionamiento;

    @Column(name = "id_estacionamiento", insertable = false, updatable = false)
    private Long idEstacionamiento;
}
