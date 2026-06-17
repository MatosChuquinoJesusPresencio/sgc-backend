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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "logs_prestamo_carrito")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
