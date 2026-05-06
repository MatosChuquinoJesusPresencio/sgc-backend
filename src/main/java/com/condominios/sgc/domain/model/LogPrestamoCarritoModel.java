package com.condominios.sgc.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;

public class LogPrestamoCarritoModel {
    private Long id;
    private TipoHabitante solicitante;
    private BigDecimal penalizacion;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private Long apartamentoId;
    private Long carritoId;
    private Long inquilinoId;
    private Long usuarioId;

    public LogPrestamoCarritoModel(Long id, TipoHabitante solicitante, Long apartamentoId, Long carritoId) {
        validarDatos(solicitante, apartamentoId, carritoId);
        this.id = id;
        this.solicitante = solicitante;
        this.apartamentoId = apartamentoId;
        this.carritoId = carritoId;
        this.fechaPrestamo = LocalDateTime.now();
        this.penalizacion = BigDecimal.ZERO;
    }

    private void validarDatos(TipoHabitante solicitante, Long apartamentoId, Long carritoId) {
        if (solicitante == null) {
            throw LogPrestamoCarritoException.solicitanteObligatorio();
        }
        if (apartamentoId == null) {
            throw LogPrestamoCarritoException.apartamentoIdObligatorio();
        }
        if (carritoId == null) {
            throw LogPrestamoCarritoException.carritoObligatorio();
        }
    }

    public Long getId() { return id; }
    public TipoHabitante getSolicitante() { return solicitante; }
    public BigDecimal getPenalizacion() { return penalizacion; }
    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public Long getApartamentoId() { return apartamentoId; }
    public Long getCarritoId() { return carritoId; }
    public Long getInquilinoId() { return inquilinoId; }
    public Long getUsuarioId() { return usuarioId; }

    public void asignarUsuarioSolicitante(Long usuarioId) {
        if (usuarioId == null) {
            throw LogPrestamoCarritoException.usuarioIdObligatorio();
        }
        if (this.inquilinoId != null) {
            throw LogPrestamoCarritoException.yaTieneSolicitanteInquilino();
        }
        this.usuarioId = usuarioId;
    }

    public void asignarInquilinoSolicitante(Long inquilinoId) {
        if (inquilinoId == null) {
            throw LogPrestamoCarritoException.inquilinoIdObligatorio();
        }
        if (this.usuarioId != null) {
            throw LogPrestamoCarritoException.yaTieneSolicitanteUsuario();
        }
        this.inquilinoId = inquilinoId;
    }

    public void registrarDevolucion(BigDecimal penalizacionAplicada) {
        if (this.fechaDevolucion != null) {
            throw LogPrestamoCarritoException.devolucionYaRegistrada();
        }
        this.fechaDevolucion = LocalDateTime.now();
        this.penalizacion = penalizacionAplicada != null ? penalizacionAplicada : BigDecimal.ZERO;
    }
}
