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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_prestamo_carrito")
@Getter
@Setter
@NoArgsConstructor
public class LogPrestamoCarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String solicitante;

    @Column(nullable = false)
    private String nombreSolicitante;

    @Column(nullable = false)
    private String dniSolicitante;

    @Column(nullable = false)
    private BigDecimal penalizacion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaDevolucion;

    @Column(name = "apartamento_id")
    private Long idApartamento;

    @Column(name = "carrito_id")
    private Long idCarrito;

    @Column(name = "inquilino_id")
    private Long idInquilino;

    @Column(name = "propietario_id")
    private Long idPropietario;

    @Column(name = "condominio_id", nullable = false)
    private Long idCondominio;
}
