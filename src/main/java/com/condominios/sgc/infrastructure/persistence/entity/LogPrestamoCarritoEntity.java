package com.condominios.sgc.infrastructure.persistence.entity;

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

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "logs_prestamo_carrito")
@NoArgsConstructor
@AllArgsConstructor
public class LogPrestamoCarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitante solicitante;

    @Column(name = "nombre_solicitante", nullable = false)
    private String nombreSolicitante;

    @Column(name = "dni_solicitante", nullable = false)
    private String dniSolicitante;

    @Column(precision = 10, scale = 2)
    private BigDecimal penalizacion;

    @Column(name = "fecha_prestamo", nullable = false)
    private Instant fechaPrestamo;

    @Column(name = "fecha_devolucion")
    private Instant fechaDevolucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private ApartamentoEntity apartamento;

    @Column(name = "id_apartamento", insertable = false, updatable = false)
    private Long idApartamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito")
    private CarritoEntity carrito;

    @Column(name = "id_carrito", insertable = false, updatable = false)
    private Long idCarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inquilino")
    private InquilinoEntity inquilino;

    @Column(name = "id_inquilino", insertable = false, updatable = false)
    private Long idInquilino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario")
    private UsuarioEntity propietario;

    @Column(name = "id_propietario", insertable = false, updatable = false)
    private Long idPropietario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoHabitante getSolicitante() { return solicitante; }
    public void setSolicitante(TipoHabitante solicitante) { this.solicitante = solicitante; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }
    public String getDniSolicitante() { return dniSolicitante; }
    public void setDniSolicitante(String dniSolicitante) { this.dniSolicitante = dniSolicitante; }
    public BigDecimal getPenalizacion() { return penalizacion; }
    public void setPenalizacion(BigDecimal penalizacion) { this.penalizacion = penalizacion; }
    public Instant getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(Instant fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public Instant getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Instant fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public ApartamentoEntity getApartamento() { return apartamento; }
    public void setApartamento(ApartamentoEntity apartamento) { this.apartamento = apartamento; }
    public Long getIdApartamento() { return idApartamento; }
    public void setIdApartamento(Long idApartamento) { this.idApartamento = idApartamento; }
    public CarritoEntity getCarrito() { return carrito; }
    public void setCarrito(CarritoEntity carrito) { this.carrito = carrito; }
    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }
    public InquilinoEntity getInquilino() { return inquilino; }
    public void setInquilino(InquilinoEntity inquilino) { this.inquilino = inquilino; }
    public Long getIdInquilino() { return idInquilino; }
    public void setIdInquilino(Long idInquilino) { this.idInquilino = idInquilino; }
    public UsuarioEntity getPropietario() { return propietario; }
    public void setPropietario(UsuarioEntity propietario) { this.propietario = propietario; }
    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
