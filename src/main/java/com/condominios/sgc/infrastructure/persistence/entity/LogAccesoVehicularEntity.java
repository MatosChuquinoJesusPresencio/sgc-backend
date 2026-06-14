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
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "logs_acceso_vehicular")
@NoArgsConstructor
@AllArgsConstructor
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public TipoHabitante getOcupante() { return ocupante; }
    public void setOcupante(TipoHabitante ocupante) { this.ocupante = ocupante; }
    public String getDatosInquilino() { return datosInquilino; }
    public void setDatosInquilino(String datosInquilino) { this.datosInquilino = datosInquilino; }
    public MetodoEntrada getMetodo() { return metodo; }
    public void setMetodo(MetodoEntrada metodo) { this.metodo = metodo; }
    public Instant getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(Instant fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public Instant getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(Instant fechaSalida) { this.fechaSalida = fechaSalida; }
    public VehiculoEntity getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }
    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long idVehiculo) { this.idVehiculo = idVehiculo; }
    public EstacionamientoEntity getEstacionamiento() { return estacionamiento; }
    public void setEstacionamiento(EstacionamientoEntity estacionamiento) { this.estacionamiento = estacionamiento; }
    public Long getIdEstacionamiento() { return idEstacionamiento; }
    public void setIdEstacionamiento(Long idEstacionamiento) { this.idEstacionamiento = idEstacionamiento; }
}
