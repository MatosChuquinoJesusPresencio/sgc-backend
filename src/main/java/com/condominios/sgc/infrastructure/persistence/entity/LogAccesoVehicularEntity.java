package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @Column(nullable = false, updatable = false)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitante ocupante;

    private String datosInquilino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoEntrada metodo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private VehiculoEntity vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacionamiento_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EstacionamientoEntity estacionamiento;
}
