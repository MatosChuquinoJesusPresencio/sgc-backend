package com.condominios.sgc.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_acceso_vehicular")
@Getter
@Setter
@NoArgsConstructor
public class LogAccesoVehicularEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = false)
    private String ocupante;

    private String datosInquilino;

    @Column(nullable = false)
    private String metodo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;

    @Column(name = "vehiculo_id")
    private Long idVehiculo;

    @Column(name = "estacionamiento_id")
    private Long idEstacionamiento;

    @Column(name = "condominio_id", nullable = false)
    private Long idCondominio;
}
